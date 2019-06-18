package io.app.service;

import io.app.dto.Transaction;

public interface ITransationService {
   public  Transaction save(Transaction transaction);
   public Transaction getTransactionById(String transactionId);
}
