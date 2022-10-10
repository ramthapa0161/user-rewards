package com.customer.rewards.controller;

import com.customer.rewards.exception.NoTransactionFoundException;
import com.customer.rewards.model.RewardsDto;
import com.customer.rewards.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class RewardsController {

    @Autowired
    private RewardsService service;

    @GetMapping("/customer/rewards")
    public ResponseEntity<RewardsDto> get(@RequestParam("customerId") Long customerId) throws NoTransactionFoundException {
        return new ResponseEntity<>(service.getRewards(customerId), HttpStatus.OK);
    }

    @GetMapping("/customer/rewards/all")
    public ResponseEntity<List<RewardsDto>> get() throws NoTransactionFoundException {
        return new ResponseEntity<>(service.getAllRewards(), HttpStatus.OK);
    }

}
