package dev.sayem.selis;

import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.models.entities.Account;
import dev.sayem.selis.domains.account.repositories.AccountRepository;
import dev.sayem.selis.domains.account.services.AccountServiceImpl;
import dev.sayem.selis.exceptions.NonExistentException;
import dev.sayem.selis.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferTest {
	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountServiceImpl accountService;

	private Account senderAccount;
	private Account receiverAccount;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		senderAccount = new Account();
		senderAccount.setAccountNumber("12345");
		senderAccount.setBalance(BigDecimal.valueOf(1000));
		senderAccount.setAccountStatus(AccountStatus.ACTIVE);

		receiverAccount = new Account();
		receiverAccount.setAccountNumber("54321");
		receiverAccount.setBalance(BigDecimal.valueOf(500));
		receiverAccount.setAccountStatus(AccountStatus.ACTIVE);
	}

	@Test
	void testTransferBalance_successful() {
		BigDecimal transferAmount = BigDecimal.valueOf(200);

		when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber()))
				.thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber()))
				.thenReturn(Optional.of(receiverAccount));

		String transactionId = accountService.transferBalance(
				senderAccount.getAccountNumber(),
				receiverAccount.getAccountNumber(),
				transferAmount
		);

		assertNotNull(transactionId);
		assertEquals(BigDecimal.valueOf(800), senderAccount.getBalance());  // 1000 - 200
		assertEquals(BigDecimal.valueOf(700), receiverAccount.getBalance());  // 500 + 200

		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		verify(accountRepository, times(2)).save(accountCaptor.capture());

		var savedAccounts = accountCaptor.getAllValues();

		assertEquals(senderAccount.getAccountNumber(), savedAccounts.get(0).getAccountNumber());
		assertEquals(BigDecimal.valueOf(800), savedAccounts.get(0).getBalance());

		assertEquals(receiverAccount.getAccountNumber(), savedAccounts.get(1).getAccountNumber());
		assertEquals(BigDecimal.valueOf(700), savedAccounts.get(1).getBalance());
	}

	@Test
	void testTransferBalance_insufficientFunds() {
		BigDecimal transferAmount = BigDecimal.valueOf(1200);  // More than sender's balance

		when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber()))
				.thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber()))
				.thenReturn(Optional.of(receiverAccount));

		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> accountService.transferBalance(
						senderAccount.getAccountNumber(),
						receiverAccount.getAccountNumber(),
						transferAmount
				)
		);
		assertEquals("Transfer amount exceeds account balance", exception.getMessage());
		verify(accountRepository, never()).save(senderAccount);
		verify(accountRepository, never()).save(receiverAccount);
	}

	@Test
	void testTransferBalance_receiverInactive() {
		BigDecimal transferAmount = BigDecimal.valueOf(100);
		receiverAccount.setAccountStatus(AccountStatus.INACTIVE);

		when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber()))
				.thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber()))
				.thenReturn(Optional.of(receiverAccount));

		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> accountService.transferBalance(
						senderAccount.getAccountNumber(),
						receiverAccount.getAccountNumber(),
						transferAmount
				)
		);
		assertEquals("Account should be active to do transacton.", exception.getMessage());
		verify(accountRepository, never()).save(senderAccount);
		verify(accountRepository, never()).save(receiverAccount);
	}

	@Test
	void testTransferBalance_senderAccountNotFound() {
		BigDecimal transferAmount = BigDecimal.valueOf(100);

		when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber()))
				.thenReturn(Optional.empty());

		NonExistentException exception = assertThrows(
				NonExistentException.class,
				() -> accountService.transferBalance(
						senderAccount.getAccountNumber(),
						receiverAccount.getAccountNumber(),
						transferAmount
				)
		);
		assertEquals("Account doesn't exist for account number: " + senderAccount.getAccountNumber(), exception.getMessage());
		verify(accountRepository, never()).save(senderAccount);
		verify(accountRepository, never()).save(receiverAccount);
	}

	@Test
	void testTransferBalance_receiverAccountNotFound() {
		BigDecimal transferAmount = BigDecimal.valueOf(100);

		when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber()))
				.thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber()))
				.thenReturn(Optional.empty());

		NonExistentException exception = assertThrows(
				NonExistentException.class,
				() -> accountService.transferBalance(
						senderAccount.getAccountNumber(),
						receiverAccount.getAccountNumber(),
						transferAmount
				)
		);
		assertEquals("Account doesn't exist for account number: " + receiverAccount.getAccountNumber(), exception.getMessage());
		verify(accountRepository, never()).save(senderAccount);
		verify(accountRepository, never()).save(receiverAccount);
	}
}
