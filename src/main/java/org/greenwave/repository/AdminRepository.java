package org.greenwave.repository;

import org.greenwave.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	@Query("select a from Admin a where a.organization.name =:x")
	public Page<Admin> getAdminByOrganization(@Param("x")String organizationName, Pageable pageable);
	
	@Query("select a from Admin a where a.localization_data.department =:x")
	public Page<Admin> getAdminByDepartment(@Param("x")String department, Pageable pageable);
	
	@Query("select a from Admin a where a.account.login = :x")
	public Admin getAdminByIdAccount(@Param("x")String login);
	
	@Query("select count(*) from Admin a join a.localization_data l where a.localization_data.department=:x")
	public int countAdminInDepartment(@Param("x")String department);

	@Query("select count(*) from Admin a where a.job='Chercheur'")
	public int countResearchers();
	
}

