package org.greenwave.repository;

import java.util.Date;

import org.greenwave.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@Query("select a from Account a where a.login =:x and a.password =:y")
	public Account getAccount(@Param("x")String login, @Param("y")String password);
	
	@Query("select a from Account a where a.login =:x ")
	public Account getAccountByUsername(@Param("x")String login);
		
	@Query("select a from Account a where a.login =:x")
	public Account checkAccount(@Param("x")String login);
	
	//count account of SimpleUser between to dates
	@Query("select count(*) from Account a where a.createdAt between :x and :y")
	public int accountCount(@Param("x") Date startDate, @Param("y") Date endDate);
	
}

