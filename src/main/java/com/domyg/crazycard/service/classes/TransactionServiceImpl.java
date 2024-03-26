package com.domyg.crazycard.service.classes;

import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.dto.TransactionDto;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.Transaction;
import com.domyg.crazycard.repository.CardRepository;
import com.domyg.crazycard.repository.StoreRepository;
import com.domyg.crazycard.repository.TransactionRepository;
import com.domyg.crazycard.service.interfaces.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CardRepository cardRepository;

    private final StoreRepository storeRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CardRepository cardRepository,
                                  StoreRepository storeRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.storeRepository = storeRepository;
    }


    @Override
    public List<TransactionDto> findTransactionsByDateAndCard(Date date, Card card) {
        List<Transaction> transactions = transactionRepository.findByDateAndCard(date, card);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByRangeAndCard(Card card, Date dateStart, Date dateEnd) {
        List<Transaction> transactions = transactionRepository.findByRangeAndCard(card, dateStart, dateEnd);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByCard(Card card) {
        List<Transaction> transactions = transactionRepository.findByCard(card);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByDateAndStore(Date date, Store store) {
        List<Transaction> transactions = transactionRepository.findByDateAndStore(date, store);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByStore(Store store) {
        List<Transaction> transactions = transactionRepository.findByStoreOrderByDateDesc(store);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionsByRangeAndStore(Store store,Date dateStart, Date dateEnd) {
        List<Transaction> transactions = transactionRepository.findByRangeAndStore(store, dateStart, dateEnd);
        return transactions.stream()
                .map((transaction) -> mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCard(cardRepository.findByNumber(transactionDto.getCardNumber()));
        transaction.setStore(storeRepository.findByName(transactionDto.getStoreName()));
        transaction.setDate(transactionDto.getDate());
        transactionRepository.save(transaction);
    }

    @Override
    public int countNegativeTransactionsByCard(CardDto cardDto) {
        Card card = cardRepository.findByNumber(cardDto.getNumber());
        return transactionRepository.countNegativeTransactionsByCard(card);
    }

    @Override
    public Double countNegativeTransactionsAmountByCard(CardDto cardDto) {
        Card card = cardRepository.findByNumber(cardDto.getNumber());
        return transactionRepository.countNegativeAmountByCard(card);
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setStoreName(transaction.getStore().getName());
        transactionDto.setCardNumber(transaction.getCard().getNumber());

        return transactionDto;

    }

}
