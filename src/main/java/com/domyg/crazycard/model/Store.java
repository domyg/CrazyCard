package com.domyg.crazycard.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name= "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String locality;

    @Column(nullable = false)
    private Boolean authorized;

    @OneToMany(mappedBy = "store")
    private List<User> users;

    // Relazione per cui a uno Store possono essere associati più utentiù
    // Questa relazione è necessaria per considerare tutti gli utenti, aventi ruolo
    // merchant, che sono associati allo store e che quindi sono dipendenti presso questo
  /*
    @OneToMany(mappedBy = "store")
    private List<User> users;

   */


}

