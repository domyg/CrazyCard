package com.domyg.crazycard.dto;

import com.domyg.crazycard.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    @NotEmpty
    private Double balance;
    @NotEmpty
    private String pin;

    @Size(min = 16, max = 16)
    @NotEmpty(message = "Il numero della carta non può essere vuoto!")
    private String number;

    @NotEmpty
    private Boolean state;

    private String ownerName;
}
