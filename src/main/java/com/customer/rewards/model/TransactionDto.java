package com.customer.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @NotNull(message = "Please provide a customer Id")
    private Long customerId;

    @NotNull(message = "Please provide a product name")
    private String productName;

    @NotNull(message = "Please provide transaction amount")
    @Min(value = 0, message = "Minimum transaction amount is zero")
    private Double amount;

}
