package io.app.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.dto.Transaction;
import io.app.repository.TransactionRepository;
import io.app.service.ITransationService;
@Service
public  class TransactionServiceImpl implements ITransationService{
    
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Override
	public Transaction save(Transaction transaction) {
		transaction.setTransactionDate(new Date());
		return transactionRepository.save(transaction);
	}


	@Override
	public Transaction getTransactionById(String transactionId) {
		return transactionRepository.getOne(transactionId);
	}

}
