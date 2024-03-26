package com.domyg.crazycard.service.interfaces;

import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.dto.TransactionDto;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.Store;

import java.sql.Date;
import java.util.List;

public interface TransactionService {


    List<TransactionDto> findTransactionsByDateAndCard(Date date, Card card);

    List<TransactionDto> findTransactionsByRangeAndCard(Card card, Date dateStart, Date dateEnd);

    List<TransactionDto> findTransactionsByCard(Card card);

    List<TransactionDto> findTransactionsByDateAndStore(Date date, Store store);
    List<TransactionDto> findTransactionsByRangeAndStore(Store store, Date dateStart, Date dateEnd);

    List<TransactionDto> findTransactionsByStore(Store store);

    void saveTransaction(TransactionDto transactionDto);

    int countNegativeTransactionsByCard(CardDto cardDto);

    Double countNegativeTransactionsAmountByCard(CardDto cardDto);

}
