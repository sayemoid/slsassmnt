package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.base.Validation;
import dev.sayem.selis.base.ValidationScope;
import dev.sayem.selis.base.ValidationUtils;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.domains.account.repositories.CustomerAccountRepository;
import dev.sayem.selis.exceptions.NonExistentException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;

@Service
public class AccountServiceImpl implements AccountService {

	private final CustomerAccountRepository accountRepository;

	public AccountServiceImpl(CustomerAccountRepository accountRepository) {
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
}
