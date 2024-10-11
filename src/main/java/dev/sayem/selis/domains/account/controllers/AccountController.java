package dev.sayem.selis.domains.account.controllers;

import dev.sayem.selis.domains.account.models.dtos.AccountDetailResponse;
import dev.sayem.selis.domains.account.models.dtos.AccountReq;
import dev.sayem.selis.domains.account.models.dtos.TransferReq;
import dev.sayem.selis.domains.account.models.dtos.TransferResponse;
import dev.sayem.selis.domains.account.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
			@Validated @RequestBody AccountReq accountReq
	) {
		var account = accountReq.toCustomerAccount(null);
		account = this.accountService.save(account);
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
			@RequestBody TransferReq req
	) {
		var tnx = this.accountService.transferBalance(req.senderAccount(), req.receiverAccount(), req.amount());
		return ResponseEntity.ok(new TransferResponse(tnx));
	}

}
