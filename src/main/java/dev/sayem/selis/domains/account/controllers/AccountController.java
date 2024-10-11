package dev.sayem.selis.domains.account.controllers;

import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.models.dtos.requests.AccountReq;
import dev.sayem.selis.domains.account.models.dtos.requests.TransferReq;
import dev.sayem.selis.domains.account.models.dtos.responses.AccountDetailResponse;
import dev.sayem.selis.domains.account.models.dtos.responses.TransferResponse;
import dev.sayem.selis.domains.account.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
	AccountService accountService;

	public AccountController(
			AccountService accountService
	) {
		this.accountService = accountService;
	}


	@PostMapping("")
	ResponseEntity<AccountDetailResponse> create(
			@Valid @RequestBody AccountReq accountReq
	) {
		var account = accountReq.toCustomerAccount(null);
		account = this.accountService.save(account);
		return ResponseEntity.ok(AccountDetailResponse.from(account));
	}

	@PatchMapping("/{accountNumber}/activate")
	ResponseEntity<AccountDetailResponse> toggleActivation(
			@PathVariable String accountNumber,
			@RequestParam AccountStatus status
	) {
		var account = this.accountService.changeStatus(accountNumber, status);
		return ResponseEntity.ok(AccountDetailResponse.from(account));
	}

	@GetMapping("/{accountNumber}")
	ResponseEntity<AccountDetailResponse> getAccountDetail(
			@PathVariable String accountNumber
	) {
		var account = this.accountService.findByAccountNumber(accountNumber);
		return ResponseEntity.ok(AccountDetailResponse.from(account));
	}

	@PatchMapping("/transfer")
	ResponseEntity<TransferResponse> transfer(
			@Valid @RequestBody TransferReq req
	) {
		var tnx = this.accountService.transferBalance(req.senderAccount(), req.receiverAccount(), req.amount());
		return ResponseEntity.ok(new TransferResponse(tnx));
	}

}
