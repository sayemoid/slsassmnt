package dev.sayem.selis.domains.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.exceptions.NonExistentException;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountDetail(
		@JsonProperty("account_number")
		String accountNumber,

		@JsonProperty("full_name")
		String fullName,

		@JsonProperty("birth_date")
		Instant birthDate,

		@JsonProperty("account_type")
		AccountType accountType,

		@JsonProperty("account_status")
		AccountStatus accountStatus,

		@JsonProperty("balance")
		BigDecimal balance,

		@JsonProperty("last_tnx_date")
		Instant lastTnxDate
) {
	public static AccountDetail from(Account account) {
		if (account == null) throw new NonExistentException(Account.class);
		return new AccountDetail(
				account.getAccountNumber(),
				account.getFullName(),
				account.getBirthDate(),
				account.getAccountType(),
				account.getAccountStatus(),
				account.getBalance(),
				account.getLastTnxDate()
		);
	}
}
