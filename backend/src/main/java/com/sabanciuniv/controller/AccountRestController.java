package com.sabanciuniv.controller;

import java.time.LocalDateTime;
import java.util.List;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sabanciuniv.model.Account;
import com.sabanciuniv.model.Transaction;
import com.sabanciuniv.repo.AccountRepository;
import com.sabanciuniv.repo.TransactionRepository;
import com.sabanciuniv.response.ResponseHandler;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping
public class AccountRestController {

	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;
	
	//private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
	
	@PostConstruct
	public void init() {
				
		Account acc1 = new Account("1111", "Jack Johns", LocalDateTime.now());
		Account acc2 = new Account("2222", "Henry Williams", LocalDateTime.now());
		
		
		
		ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, accountRepository.save(acc1));
		ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, accountRepository.save(acc2));
		
		Transaction tran1 = new Transaction("1111", "2222", 1500, acc1, acc2);
		Transaction tran2 = new Transaction("2222", "1111", 2500, acc2, acc1);
		
		tran1.getFromId();
		
		ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, transactionRepository.save(tran1));
		ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, transactionRepository.save(tran2));
	}
	
	@GetMapping("/accounts")
	public List<Account> accounts() {
		
		return accountRepository.findAll();
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<Object> summaryAccounts(@PathVariable String accountId) {
		
		List<Account> accountSummary = accountRepository.findWithParamId(accountId);
		
		if (accountSummary != null) {
			
			return ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, accountSummary);
		}
		
		return ResponseHandler.responseBuilder("ERROR: account doesnt exist!", HttpStatus.OK, null);
	}
	
	@PostMapping("/account/save")
	public ResponseEntity<Object> saveAccount(@RequestBody Account account) {
		
		if (account.getId() == null || account.getOwner() == null) {
			
			return ResponseHandler.responseBuilder("ERROR: missing fields", HttpStatus.OK, null);
		}
		else {
			
			Account accountSaved = accountRepository.save(account);
			
			return ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, accountSaved);
		}
	}
	
	@GetMapping("/transactions")
	public List<Transaction> transactions() {
		
		return transactionRepository.findAll();
	} 
	
	@PostMapping("/transaction/save")
	public ResponseEntity<Object> saveTransaction(@RequestBody Transaction transaction) {
		
		Account accountFrom = accountRepository.findById(transaction.getFromId()).get();
		Account accountTo = accountRepository.findById(transaction.getToId()).get();
				
		if (accountFrom.getId() != null && accountFrom.getId() != null && transaction.getAmount() >= 0.0) {
			
			Transaction tran = new Transaction(accountFrom.getId(), accountTo.getId(), 
					transaction.getAmount(), accountFrom, accountTo);
			
			Transaction transactionSaved = transactionRepository.save(tran);
			
			return ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, transactionSaved);
			
		}else {
			
			if (accountFrom.getId() == null || accountFrom.getId() == null) {
				
				return ResponseHandler.responseBuilder("ERROR: account id", HttpStatus.OK, null);
				
			}else {
				
				return ResponseHandler.responseBuilder("ERROR: missing fields", HttpStatus.OK, null);
			}
		}
	}
	
	@GetMapping("/transaction/to/{accountId}")
	public ResponseEntity<Object> IncomingTrans(@PathVariable String accountId) {
			
		if (accountId != null) {
			
			List<Transaction> incomingTrans = transactionRepository.findWithParamTranId(accountId);
			
			return ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, incomingTrans);
			
		}else {
			
			return ResponseHandler.responseBuilder("ERROR: account doesn’t exist", HttpStatus.OK, null);
		}
	}
	
	@GetMapping("/transaction/from/{accountId}")
	public ResponseEntity<Object> OutgoingTrans(@PathVariable String accountId) {
			
		if (accountId != null) {
			
			List<Transaction> outgoingTrans = transactionRepository.findWithParamTranId(accountId);
			
			return ResponseHandler.responseBuilder("SUCCESS", HttpStatus.OK, outgoingTrans);
			
		}else {
			
			return ResponseHandler.responseBuilder("ERROR: account doesn’t exist", HttpStatus.OK, null);
		}
	}
}