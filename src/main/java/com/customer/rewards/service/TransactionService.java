package com.customer.rewards.service;

import com.customer.rewards.entity.Transaction;
import com.customer.rewards.model.TransactionDto;

public interface TransactionService {

    Transaction add(TransactionDto transactionDto);

}
