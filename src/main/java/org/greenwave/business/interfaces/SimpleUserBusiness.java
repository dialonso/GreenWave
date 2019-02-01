package org.greenwave.business.interfaces;

import org.greenwave.model.SimpleUser;
import org.springframework.data.domain.Page;

public interface SimpleUserBusiness {
	
	public SimpleUser create(SimpleUser simpleUser);

	public void update(Long id, SimpleUser simpleUser);

	public void delete(Long id);
	
	public Page<SimpleUser> findByAdmin(Long idSimpleUser, Long idAdmin, int page, int size);
	
	public Page <SimpleUser> findAll(int page, int size);
	
	public SimpleUser findByIdAccount(String login);
	
	public SimpleUser findById(Long id);
	
	public int countInDepartment(String department);
	
	public int findAllReasearchers();
	
	public Page <SimpleUser> findAllInDepartment(String department, int page, int size);

}
