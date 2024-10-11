package dev.sayem.selis.domains.account.services;

import dev.sayem.selis.base.BaseServiceV1;
import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.models.entities.Account;

import java.math.BigDecimal;

public interface AccountService extends BaseServiceV1<Account> {
	Account save(Account account);

	Account find(Long id);

	Account findByAccountNumber(String accountNumber);

	Account changeStatus(String accountNumber, AccountStatus status);

	String transferBalance(String senderAccNumber, String receiverAccNumber, BigDecimal amount);
}

