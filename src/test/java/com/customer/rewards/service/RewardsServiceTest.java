package com.customer.rewards.service;

import com.customer.rewards.entity.Transaction;
import com.customer.rewards.exception.NoTransactionFoundException;
import com.customer.rewards.model.RewardsDto;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.impl.RewardsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RewardsServiceTest {

    @InjectMocks
    private RewardsService rewardService = new RewardsServiceImpl();

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testWithRewards() throws NoTransactionFoundException {
        Mockito.when(transactionRepository.findByCustomerIdAndDateGreaterThanEqual(any(),any())).thenReturn(mockTransactions());
        RewardsDto rewards = rewardService.getRewards(1L);
        Assertions.assertEquals(550, rewards.getTotalRewards());
    }

    @Test
    public void testTransactionWithNoRewards() throws NoTransactionFoundException {
        Mockito.when(transactionRepository.findByCustomerIdAndDateGreaterThanEqual(any(),any())).thenReturn(mockAmountsLessThan50());
        RewardsDto rewards = rewardService.getRewards(1L);
        Assertions.assertEquals(0, rewards.getTotalRewards());
    }

    public List<Transaction> mockTransactions() {
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
        return transactions;
    }

    public List<Transaction> mockAmountsLessThan50() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().customerId(1L).amount(10.0).date(Date.from(LocalDate.of(2022, 10, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(30.0).date(Date.from(LocalDate.of(2022, 10, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(40.0).date(Date.from(LocalDate.of(2022, 10, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 10, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        return transactions;
    }

}
