package com.domyg.crazycard.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name= "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name="card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

}
