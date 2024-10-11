package dev.sayem.selis.domains.account.controllers;

import dev.sayem.selis.domains.account.models.dtos.AccountDetail;
import dev.sayem.selis.domains.account.models.dtos.AccountReq;
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
	ResponseEntity<AccountDetail> create(
			@Validated @RequestBody AccountReq accountReq
	) {
		var account = accountReq.toCustomerAccount(null);
		account = this.accountService.save(account);
		return ResponseEntity.ok(AccountDetail.from(account));
	}

	@GetMapping("/{accountNumber}")
	ResponseEntity<AccountDetail> getAccountDetail(
			@PathVariable String accountNumber
	) {
		var account = this.accountService.findByAccountNumber(accountNumber);
		return ResponseEntity.ok(AccountDetail.from(account));
	}

}
