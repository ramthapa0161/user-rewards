package com.customer.rewards.controller;


import com.customer.rewards.service.TransactionService;
import com.customer.rewards.entity.Transaction;
import com.customer.rewards.model.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> add(@Valid @RequestBody TransactionDto transactionDto){
        return new ResponseEntity<>(transactionService.add(transactionDto), HttpStatus.OK);
    }

}
