package com.domyg.crazycard.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardTransactionDto {

    @NotEmpty
    private String card;

    private Boolean state;

    private String owner;
    @NotEmpty
    private Integer transactionsNumber;
    private Double totalSpent;
    private Double averageSpent;
}
