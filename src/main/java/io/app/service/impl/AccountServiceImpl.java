package io.app.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.dto.Account;
import io.app.repository.AccountRepository;
import io.app.service.IAccountService;
@Service
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
    private AccountRepository accountRepository;
	
	@Override
	public Account createAccount(Account account) {
		account.setTheLastTrxnDate(new Date());
		return accountRepository.save(account);
	}

	@Override
	public Account getAccountDetail(long accountNumber) {
		return accountRepository.getOne(accountNumber);
	}

}
