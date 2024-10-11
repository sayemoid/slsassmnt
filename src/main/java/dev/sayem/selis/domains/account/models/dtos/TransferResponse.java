package dev.sayem.selis.domains.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferResponse(
		@JsonProperty("tnx_id")
		String tnxId
) {
}
