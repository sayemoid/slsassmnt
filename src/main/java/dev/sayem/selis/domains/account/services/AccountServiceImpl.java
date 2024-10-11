package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.domains.account.repositories.CustomerAccountRepository;
import dev.sayem.selis.exceptions.NonExistentException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

	private final CustomerAccountRepository accountRepository;

	public AccountServiceImpl(CustomerAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	@Override
	public Account save(Account account) {
		return new Account();
	}

	@Override
	public Account findByAccountNumber(String accountNumber) {
		return this.accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new NonExistentException("Account doesn't exist for account number: " + accountNumber));
	}

}
