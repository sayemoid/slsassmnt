package dev.sayem.selis.domains.account.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.domains.account.models.entities.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountReq(
		@Size(min = 6, max = 14, message = "Account number should be between 6-14 chars")
		@JsonProperty("account_number")
		String accountNumber,

		@NotBlank(message = "Name can not be empty")
		@JsonProperty("full_name")
		String fullName,

		@NotNull(message = "Birthdate is required")
		@JsonProperty("birth_date")
		Instant birthDate,

		@NotNull(message = "Account type is required.")
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
