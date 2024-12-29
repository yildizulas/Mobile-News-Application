package com.sabanciuniv.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sabanciuniv.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String>{

	@Query("{id: '?0'}")
	public List<Transaction> findWithParamTranId(String id);
}
