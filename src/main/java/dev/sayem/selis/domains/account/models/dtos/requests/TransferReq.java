package dev.sayem.selis.domains.account.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransferReq(
		@NotBlank(message = "Sender account must not be empty")
		@JsonProperty("sender_account")
		String senderAccount,

		@NotBlank(message = "Receiver account must not be empty")
		@JsonProperty("receiver_account")
		String receiverAccount,

		@Min(value = 1, message = "Amount can't be <=0")
		@JsonProperty("amount")
		BigDecimal amount

) {
}
