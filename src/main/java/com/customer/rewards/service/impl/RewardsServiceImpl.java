package com.customer.rewards.service.impl;

import com.customer.rewards.entity.Transaction;
import com.customer.rewards.exception.NoTransactionFoundException;
import com.customer.rewards.model.RewardsDto;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.RewardsService;
import com.customer.rewards.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    private TransactionRepository repository;


    @Override
    public RewardsDto getRewards(Long customerId) throws NoTransactionFoundException {
        List<Transaction> transactions = repository.findByCustomerIdAndDateGreaterThanEqual(customerId,DateUtil.getOldStartingDate(-3));
        if(transactions.isEmpty()){
            throw new NoTransactionFoundException("No transaction present for the customer Id "+customerId);
        }
        Map<String, Double> rewards = getRewardsMap(transactions);

        return RewardsDto.builder()
                .customerId(customerId)
                .totalRewards(rewards.values().stream().reduce(0.0,Double::sum))
                .rewards(rewards)
                .build();
    }

    private Map<String, Double> getRewardsMap(List<Transaction> transactions) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM");
        return transactions.stream()
                .collect(Collectors.toMap(transaction -> sdf.format(transaction.getDate()), this::calculateRewards,Double::sum));
    }

    @Override
    public List<RewardsDto> getAllRewards() throws NoTransactionFoundException {
        List<Transaction> transactions = repository.findByDateGreaterThanEqual(DateUtil.getOldStartingDate(-3));
        if(transactions.isEmpty()){
            throw new NoTransactionFoundException("No transaction present");
        }
        Map<Long, List<Transaction>> userTransactions = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));

        return userTransactions.entrySet().stream().map(entry->{
            Map<String, Double> rewardsMap = getRewardsMap(entry.getValue());
            return RewardsDto.builder()
                    .customerId(entry.getKey())
                    .totalRewards(rewardsMap.values().stream().reduce(0.0,Double::sum))
                    .rewards(rewardsMap)
                    .build();
        }).collect(Collectors.toList());
    }

    private Double calculateRewards(Transaction transaction) {
        Double amount = transaction.getAmount();
        double rewards=0.0;
        double diff = Math.max(0,amount-50);
        rewards=rewards+diff;
        diff = Math.max(0,diff-50);
        rewards=rewards+diff;
        log.debug("Calculated reward for value "+transaction.getAmount()+" is "+rewards);
        return rewards;
    }

    @PostConstruct
    public void mockTransactions(){
        List<Transaction> transactions =new ArrayList<>();
        transactions.add(Transaction.builder().customerId(1L).amount(120.0).date(Date.from(LocalDate.of(2022, 7, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(140.0).date(Date.from(LocalDate.of(2022, 7, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(70.0).date(Date.from(LocalDate.of(2022, 7, 28).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 7, 28).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(10.0).date(Date.from(LocalDate.of(2022, 7, 29).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(80.0).date(Date.from(LocalDate.of(2022, 8, 2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(90.0).date(Date.from(LocalDate.of(2022, 8, 3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(30.0).date(Date.from(LocalDate.of(2022, 8, 4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(70.0).date(Date.from(LocalDate.of(2022, 9, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(150.0).date(Date.from(LocalDate.of(2022, 9, 2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 9, 3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(90.0).date(Date.from(LocalDate.of(2022, 9, 8).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        transactions.add(Transaction.builder().customerId(1L).amount(80.0).date(Date.from(LocalDate.of(2022, 9, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Product Name").build());
        repository.saveAll(transactions);
    }
}
