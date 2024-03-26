package com.domyg.crazycard.repository;

import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateAndStore(Date date, Store store);

    List<Transaction> findByCard(Card card);
    List<Transaction> findByDate(Date date);

    List<Transaction> findByDateAndCard(Date date, Card card);

    List<Transaction> findByStoreOrderByDateDesc(Store store);

    @Query("SELECT t FROM Transaction t WHERE t.store=?1 AND t.date BETWEEN ?2 AND ?3")
    List<Transaction> findByRangeAndStore(Store store, Date dateStart, Date dateEnd);

    @Query("SELECT t FROM Transaction t WHERE t.card=?1 AND t.date BETWEEN ?2 AND ?3")
    List<Transaction> findByRangeAndCard(Card card, Date dateStart, Date dateEnd);

    @Query("""
            SELECT COUNT(t) AS total
            FROM Transaction t
            WHERE t.card=?1 AND t.amount < 0""")
    int countNegativeTransactionsByCard(Card card);


    @Query("""
            SELECT SUM(t.amount) AS total
            FROM Transaction t
            WHERE t.card = ?1 AND t.amount < 0""")
    Double countNegativeAmountByCard(Card card);
}
