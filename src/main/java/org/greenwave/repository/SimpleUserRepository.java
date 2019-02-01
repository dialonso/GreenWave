package org.greenwave.repository;

import org.greenwave.model.SimpleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {

	@Query("select s from SimpleUser s where s.admin.id =:x")
	public Page<SimpleUser> getUserCreatedByAdmin(@Param("x")Long idAdmin, Pageable pageable);
	
	@Query("select a from SimpleUser a where a.localization_data.department =:x")
	public Page<SimpleUser> getUserByDepartment(@Param("x")String department, Pageable pageable);
	
	@Query("select a from SimpleUser a where a.account.login = :x")
	public SimpleUser getSimpleByIdAccount(@Param("x")String idAccount);
	
	@Query("select count(*) from SimpleUser a join a.localization_data l where a.localization_data.department=:x")
	public int countUsersInDepartment(@Param("x")String department);
	
	//list all account ids of simpleUser

	@Query("select count(*) from SimpleUser a where a.job='Chercheur'")
	public int countResearchers();

}

