package dev.sayem.selis.domains.account.controllers;

import dev.sayem.selis.domains.account.models.dtos.CustomerAccountDetail;
import dev.sayem.selis.domains.account.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
	AccountService accountService;

	public AccountController(
			AccountService accountService
	) {
		this.accountService = accountService;
	}



	@GetMapping("/{accountNumber}")
	ResponseEntity<CustomerAccountDetail> getAccountDetail(
			@PathVariable String accountNumber
	) {
		var account = this.accountService.findByAccountNumber(accountNumber);
		return ResponseEntity.ok(CustomerAccountDetail.from(account));
	}

}
