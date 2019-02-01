package org.greenwave.business;

import java.util.Date;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.model.Account;
import org.greenwave.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountBusinessImpl implements AccountBusiness {
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public Account create(Account c) {
		Account account = accountRepository.save(c);
		return account;
	}

	@Override
	public void update(Long id, Account account) {
		Account acc = accountRepository.findOne(id);
		acc = account;
		accountRepository.save(acc);			
	}

	@Override
	public Account find(String login, String password) {
		Account account = accountRepository.getAccount(login, password);
		return account;
	}

	@Override
	public Account findByUsername(String login) {
		return accountRepository.getAccountByUsername(login);
	}

	@Override
	public Account check(String login) {
		Account account = accountRepository.checkAccount(login);
		return account;
	}

	@Override
	public void delete(Long id) {
		Account account = accountRepository.findOne(id);
		accountRepository.delete(account);		
	}
	

	@Override
	public int countBetweenDatas(Date start, Date end) {
		return accountRepository.accountCount(start, end);
	}

}
