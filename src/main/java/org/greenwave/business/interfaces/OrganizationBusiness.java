package org.greenwave.business.interfaces;

import org.greenwave.model.Organization;
import org.springframework.data.domain.Page;

public interface OrganizationBusiness {
	
	public Organization create(Organization organization);

	public Organization update(Long id);

	public void delete(Long id);
	
	public Page<Organization> findAll(int page, int size);
	
	public int countInDepartment(String department);
	
	public Organization find(String login);

}
