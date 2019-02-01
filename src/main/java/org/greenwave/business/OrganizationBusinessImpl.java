package org.greenwave.business;

import org.greenwave.business.interfaces.OrganizationBusiness;
import org.greenwave.model.Organization;
import org.greenwave.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationBusinessImpl implements OrganizationBusiness {
	

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public Organization create(Organization organization) {
		Organization org= organizationRepository.save(organization);
		return org;
	}

	@Override
	public Organization update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		Organization organization = organizationRepository.findOne(id);
		organizationRepository.delete(organization);		
	}

	@Override
	public Page<Organization> findAll(int page, int size) {
		return organizationRepository.findAll(new PageRequest(page, size));
	}

	@Override
	public int countInDepartment(String department) {
		return organizationRepository.countOrganizationsInDepartment(department);
	}

	@Override
	public Organization find(String login) {
		return organizationRepository.getOrganizationByUsername(login);
	}

}
