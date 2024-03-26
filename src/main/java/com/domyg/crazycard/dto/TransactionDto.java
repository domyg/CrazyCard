package com.domyg.crazycard.dto;

import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.Store;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @NotEmpty
    private Date date;
    @NotEmpty
    private Double amount;
    @NotEmpty
    private String cardNumber;
    @NotEmpty
    private String storeName;
}
