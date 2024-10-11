package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.domains.account.models.entities.Account;

public interface AccountService {
	Account save(Account account);

	Account findByAccountNumber(String accountNumber);
}

