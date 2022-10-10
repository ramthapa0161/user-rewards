package com.customer.rewards.service.impl;

import com.customer.rewards.entity.Transaction;
import com.customer.rewards.model.TransactionDto;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction add(TransactionDto dto) {
        return transactionRepository.save(map(dto));
    }

    private Transaction map(TransactionDto dto) {
        return Transaction.builder()
                .productName(dto.getProductName())
                .amount(dto.getAmount())
                .customerId(dto.getCustomerId())
                .date(new Date())
                .build();
    }
}
