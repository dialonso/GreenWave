package org.greenwave.business.interfaces;

import org.greenwave.model.Admin;
import org.springframework.data.domain.Page;

public interface AdminBusiness {
	
	public Admin create(Admin admin);

	public void update(Long id, Admin admin);

	public void delete(Long id);
	
	public Page<Admin> findByOrganization(String name, int page, int size);
	
	public Page <Admin> findAll(int page, int size);
	
	public Page <Admin> findAllInDepartment(String department, int page, int size);

	public Admin findByIdAccount(String login);
	
	public int countInDepartment(String department);
	
	public Admin findById(Long id);
	
	public int findAllReasearchers();

}
