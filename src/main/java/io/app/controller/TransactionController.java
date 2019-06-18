package io.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.app.dto.Account;
import io.app.dto.Transaction;
import io.app.event.OnTransactionComplete;
import io.app.service.IAccountService;
import io.app.service.ITransationService;
@Controller
public class TransactionController {
	@Autowired
	private  ITransationService transationService;
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@RequestMapping(value="/doTransaction",method=RequestMethod.POST)
	public String saveTransaction(@RequestParam("accountNumber")long accountNumber,@ModelAttribute Transaction transaction,ModelMap map) {
		System.out.println(transaction);
		Account account=accountService.getAccountDetail(accountNumber);
		double theTotalDue=account.getTheTotalDue();
		double transactionAmount=transaction.getTransactionAmount();
		double theDueAfterPayment=theTotalDue-transactionAmount;
		transaction.setTheTotalDue(theTotalDue);
		transaction.setTheDueAfterPayment(theDueAfterPayment);
		account.setTheTotalDue(theDueAfterPayment);
		transaction.setAccount(account);
		transaction=transationService.save(transaction);
		applicationEventPublisher.publishEvent(new OnTransactionComplete(transaction));
		map.put("message"," The transaction has been completed with transaction Id "+transaction.getTransactionId());
		return "redirect:/getAccountDetails?accountNumber="+accountNumber;
	}
	
	@RequestMapping("/getTrxnPage")
	public String trxPage() {
		return "TransactionById";
	}
	
	@RequestMapping(value="/getTrxnById",method=RequestMethod.GET)
	public String getTransactionById(@RequestParam("transactionId")String transactionId,ModelMap map) {
		map.put("transaction", transationService.getTransactionById(transactionId));
		return "TransactionById";
	}
	
}
