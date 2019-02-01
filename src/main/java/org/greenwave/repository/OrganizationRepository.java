package org.greenwave.repository;

import org.greenwave.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query("select count(*) from Organization a join a.localization_org l where a.localization_org.department=:x")
	public int countOrganizationsInDepartment(@Param("x")String department);
	
	@Query("select a from Organization a join a.account l where a.account.login=:x")
	public Organization getOrganizationByUsername(@Param("x")String login);

}
