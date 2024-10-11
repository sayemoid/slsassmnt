package dev.sayem.selis.domains.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.exceptions.NonExistentException;

public record CustomerAccountBrief(
		@JsonProperty("account_number")
		String accountNumber,
		@JsonProperty("full_name")
		String fullName,
		@JsonProperty("account_type")
		AccountType accountType
) {
	CustomerAccountBrief from(Account account) {
		if (account == null) throw new NonExistentException(Account.class);
		return new CustomerAccountBrief(
				account.getAccountNumber(),
				account.getFullName(),
				account.getAccountType()
		);
	}
}
