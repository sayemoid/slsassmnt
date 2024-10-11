package dev.sayem.selis;

import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.domains.account.repositories.CustomerAccountRepository;
import dev.sayem.selis.domains.account.services.AccountServiceImpl;
import dev.sayem.selis.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

	@Mock
	private CustomerAccountRepository accountRepository;

	private AccountServiceImpl accountService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	@Test
	void testSave_whenAccountNumberIsUnique_shouldSaveAccount() {
		var account = new Account();
		account.setAccountNumber("12345");
		account.setId(1L);

		when(this.accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Optional.empty());
		when(accountRepository.save(account)).thenReturn(account);

		Account result = accountService.save(account);

		assertNotNull(result);
		assertEquals(account.getAccountNumber(), result.getAccountNumber());
		verify(accountRepository).save(account);
	}

	@Test
	void testSave_whenAccountNumberIsNotUnique_shouldThrowValidationException() {
		var account = new Account();
		account.setAccountNumber("12345");
		account.setId(1L);

		// existing account
		Account existingAccount = new Account();
		existingAccount.setAccountNumber("12345");
		existingAccount.setId(2L);

		when(this.accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(existingAccount));

		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> accountService.save(account)
		);

		assertEquals("Account number must be unique", exception.getMessage());
		verify(accountRepository, never()).save(any());
	}
}
