package dev.sayem.selis.domains.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.domains.account.models.entities.Account;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountReq(
		@JsonProperty("account_number")
		String accountNumber,
		@JsonProperty("full_name")
		String fullName,
		@JsonProperty("birth_date")
		Instant birthDate,
		@JsonProperty("account_type")
		AccountType accountType
) {

	public Account toCustomerAccount(
			Account existing
	) {
		var account = existing != null ? existing : new Account();
		account.setAccountNumber(this.accountNumber);
		account.setFullName(this.fullName);
		account.setBirthDate(this.birthDate);
		account.setAccountType(this.accountType);
		account.setAccountStatus(
				account.getAccountStatus() == null ? AccountStatus.INACTIVE : account.getAccountStatus()
		);
		account.setBalance(
				account.getBalance() == null ? BigDecimal.valueOf(0) : account.getBalance()
		);
		return account;
	}

}
