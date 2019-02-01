package org.greenwave.business;

import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.model.Admin;
import org.greenwave.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminBusinessImpl implements AdminBusiness {
	
	@Autowired
	AdminRepository adminRepository;

	@Override
	public Admin create(Admin admin) {
		Admin administrator=adminRepository.save(admin);
		return administrator;
	}

	@Override
	public void update(Long id, Admin admin) {
		Admin ad = adminRepository.findOne(id);
		ad = admin;
		adminRepository.save(admin);		
	}

	@Override
	public void delete(Long id) {
		Admin admin = adminRepository.findOne(id);
		adminRepository.delete(admin);		
	}

	@Override
	public Page<Admin> findByOrganization(String name, int page, int size) {
		return adminRepository.getAdminByOrganization(name, new PageRequest(page, size));
	}

	@Override
	public Page<Admin> findAll(int page, int size) {
		return adminRepository.findAll(new PageRequest(page, size));
	}

	@Override
	public Page<Admin> findAllInDepartment(String department, int page, int size) {
		return adminRepository.getAdminByDepartment(department, new PageRequest(page, size));
	}

	@Override
	public Admin findByIdAccount(String login) {
		return adminRepository.getAdminByIdAccount(login);
	}

	@Override
	public int countInDepartment(String department) {
		return adminRepository.countAdminInDepartment(department);
	}

	@Override
	public Admin findById(Long id) {
		return adminRepository.findOne(id);
	}

	@Override
	public int findAllReasearchers() {
		return adminRepository.countResearchers();
	}

}
