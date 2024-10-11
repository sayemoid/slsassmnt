package dev.sayem.selis.domains.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TransferReq (
	@JsonProperty("sender_account")
	String senderAccount,

	@JsonProperty("receiver_account")
	String receiverAccount,

	@JsonProperty("amount")
	BigDecimal amount

){}
