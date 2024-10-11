package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.base.BaseServiceV1;
import dev.sayem.selis.domains.account.models.entities.Account;

public interface AccountService extends BaseServiceV1<Account> {
	Account save(Account account);

	Account find(Long id);
	Account findByAccountNumber(String accountNumber);
}

