package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.base.Validation;
import dev.sayem.selis.base.ValidationScope;
import dev.sayem.selis.base.ValidationUtils;
import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.domains.account.repositories.AccountRepository;
import dev.sayem.selis.exceptions.NonExistentException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Set;
import java.util.function.Function;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account save(Account account) {
		applyValidations(account);
		return this.accountRepository.save(account);
	}

	@Override
	public Account find(Long id) {
		return this.accountRepository.find(id).orElse(null);
	}

	@Override
	public Account findByAccountNumber(String accountNumber) {
		return this.accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new NonExistentException("Account doesn't exist for account number: " + accountNumber));
	}

	@Override
	public Account changeStatus(String accountNumber, AccountStatus status) {
		return this.accountRepository.findByAccountNumber(accountNumber)
				.map(account -> {
					account.setAccountStatus(status);
					return accountRepository.save(account);
				})
				.orElseThrow(() -> new NonExistentException("Account doesn't exist for accountNumber: " + accountNumber));
	}

	@Transactional
	@Override
	public String transferBalance(String senderAccNumber, String receiverAccNumber, BigDecimal amount) {
		var senderAccount = this.accountRepository.findByAccountNumber(senderAccNumber)
				.orElseThrow(() -> new NonExistentException("Account doesn't exist for account number: " + senderAccNumber));
		var receiverAccount = this.accountRepository.findByAccountNumber(receiverAccNumber)
				.orElseThrow(() -> new NonExistentException("Account doesn't exist for account number: " + receiverAccNumber));

		// Apply validations for sender
		Set.of(
				AccountValidations.transferAmountShouldNotExceedBalance(amount),
				AccountValidations.accountShouldBeActive()
		).forEach(accountValidation -> {
			accountValidation.apply(senderAccount, ValidationScope.MODIFY);
		});

		// Apply validations for receiver
		Set.of(
				AccountValidations.accountShouldBeActive()
		).forEach(v -> v.apply(receiverAccount, ValidationScope.MODIFY));

		senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

		this.accountRepository.save(senderAccount);
		this.accountRepository.save(receiverAccount);
		/*
		Ideally there should be a transaction table where this transaction details would be saved and
		tnx it would be returned.
		For now sending a random number as tnx id.
		 */
		return String.valueOf(100000 + new SecureRandom().nextInt(89999));
	}

	@Override
	public Set<Validation<Account>> validations() {
		return Set.of(
				AccountValidations.uniqueValidation(accountNumber ->
						this.accountRepository.findByAccountNumber(accountNumber).orElse(null)
				)
		);
	}

	@Override
	public JpaRepository<Account, Long> getRepository() {
		return this.accountRepository;
	}
}

class AccountValidations {
	static Validation<Account> uniqueValidation(
			Function<String, Account> getAccount
	) {
		return ValidationUtils.genericValidation(
				"Account number must be unique",
				Set.of(ValidationScope.WRITE, ValidationScope.MODIFY),
				null,
				(account -> {
					var existing = getAccount.apply(account.getAccountNumber());
					return ValidationUtils.validateUniqueOperation(existing, account);
				})
		);
	}

	static Validation<Account> transferAmountShouldNotExceedBalance(BigDecimal amount) {
		return ValidationUtils.genericValidation(
				"Transfer amount exceeds account balance",
				Set.of(ValidationScope.MODIFY),
				null,
				(account) -> account.getBalance().subtract(amount).doubleValue() > 0
		);
	}

	static Validation<Account> accountShouldBeActive() {
		return ValidationUtils.genericValidation(
				"Account should be active to do transacton.",
				Set.of(ValidationScope.MODIFY),
				null,
				account -> AccountStatus.ACTIVE.equals(account.getAccountStatus())
		);
	}
}
