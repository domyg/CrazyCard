package com.domyg.crazycard.repository;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByNumber(String number);

    @Query("SELECT c FROM Card c WHERE c.balance=?1 AND c.user = null AND c.state = true")
    List<Card> findFreeCardsByBalance(Double balance);

    List<Card> findCardsByUser(User user);

}

