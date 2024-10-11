package dev.sayem.selis.domains.account.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferResponse(
		@JsonProperty("tnx_id")
		String tnxId
) {
}
