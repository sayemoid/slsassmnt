package dev.sayem.selis.domains.account.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.exceptions.NonExistentException;

public record AccountBriefResponse(
		@JsonProperty("account_number")
		String accountNumber,
		@JsonProperty("full_name")
		String fullName,
		@JsonProperty("account_type")
		AccountType accountType
) {
	AccountBriefResponse from(Account account) {
		if (account == null) throw new NonExistentException(Account.class);
		return new AccountBriefResponse(
				account.getAccountNumber(),
				account.getFullName(),
				account.getAccountType()
		);
	}
}
