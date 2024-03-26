package com.domyg.crazycard.dto;

import com.domyg.crazycard.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String locality;

    private Boolean authorized;

}
