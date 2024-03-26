package com.domyg.crazycard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name= "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 16, max = 16)
    @Column(unique = true)
    private String number;

    @Size(min = 6, max = 6)
    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Boolean state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", unique = false)
    private User user;

}
