package org.greenwave.business;


import org.greenwave.repository.SimpleUserRepository;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SimpleUserBusinessImpl implements SimpleUserBusiness {
	
	@Autowired
	SimpleUserRepository simpleUserRepository;

	@Override
	public SimpleUser create(SimpleUser simpleUser) {
		SimpleUser user = simpleUserRepository.save(simpleUser);
		return user;
	}

	@Override
	public void update(Long id, SimpleUser user) {
		SimpleUser n_user = simpleUserRepository.findOne(id);
		n_user = user;
		simpleUserRepository.save(n_user);		
	}

	@Override
	public void delete(Long id) {
		SimpleUser user = simpleUserRepository.findOne(id);
		simpleUserRepository.delete(user);		
	}

	@Override
	public Page<SimpleUser> findByAdmin(Long idUser, Long idAdmin, int page, int size) {
		return simpleUserRepository.getUserCreatedByAdmin(idAdmin, new PageRequest(page, size));
	}

	@Override
	public Page<SimpleUser> findAll(int page, int size) {
		return simpleUserRepository.findAll(new PageRequest(page, size));
	}

	@Override
	public SimpleUser findByIdAccount(String login) {
		return simpleUserRepository.getSimpleByIdAccount(login);
	}

	@Override
	public SimpleUser findById(Long id) {
		return simpleUserRepository.findOne(id);
	}

	@Override
	public int countInDepartment(String department) {
		return simpleUserRepository.countUsersInDepartment(department);
	}

	@Override
	public int findAllReasearchers() {
		return simpleUserRepository.countResearchers();
	}

	@Override
	public Page<SimpleUser> findAllInDepartment(String department, int page, int size) {
		return simpleUserRepository.getUserByDepartment(department, new PageRequest(page, size));
	}

}
