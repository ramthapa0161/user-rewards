package com.customer.rewards.service;


import com.customer.rewards.model.RewardsDto;
import com.customer.rewards.exception.NoTransactionFoundException;

import java.util.List;

public interface RewardsService {

    RewardsDto getRewards(Long customerId) throws NoTransactionFoundException;

    List<RewardsDto> getAllRewards() throws NoTransactionFoundException;

}
