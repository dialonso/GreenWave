package org.greenwave.business.interfaces;

import java.util.Date;

import org.greenwave.model.Account;

public interface AccountBusiness {
	
	public Account create(Account c);
	public void update(Long id, Account account);
	
	public Account find(String login, String password);
	public Account findByUsername(String login);

	public Account check(String login);
	
	public void delete(Long id);

	public int countBetweenDatas(Date start, Date end);

}
