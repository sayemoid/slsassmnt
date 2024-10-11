package dev.sayem.selis.domains.account.repositories;

import dev.sayem.selis.domains.account.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT a FROM Account a WHERE a.id=:id")
	Optional<Account> find(@Param("id") Long id);

	@Query("SELECT a FROM Account a WHERE a.accountNumber=:accountNumber")
	Optional<Account> findByAccountNumber(@Param("accountNumber") String accountNumber);

}
