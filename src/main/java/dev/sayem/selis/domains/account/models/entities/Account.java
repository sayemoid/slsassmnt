package dev.sayem.selis.domains.account.models.entities;

import dev.sayem.selis.domains.account.enums.AccountStatus;
import dev.sayem.selis.domains.account.enums.AccountType;
import dev.sayem.selis.base.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "customer_account")
public class Account extends BaseEntity {

	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "birth_date", nullable = false)
	private Instant birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_status", nullable = false)
	private AccountStatus accountStatus;

	@Column(name = "balance", precision = 19, scale = 4)
	private BigDecimal balance;

	@Column(name = "last_tnx_date")
	private Instant lastTnxDate;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Instant getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Instant getLastTnxDate() {
		return lastTnxDate;
	}

	public void setLastTnxDate(Instant lastTnxDate) {
		this.lastTnxDate = lastTnxDate;
	}
}
