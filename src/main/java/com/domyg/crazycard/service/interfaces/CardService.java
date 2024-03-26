package com.domyg.crazycard.service.interfaces;

import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.User;

import java.util.List;

public interface CardService {
    void saveCard(CardDto cardDto);

    void updateCardCredit(CardDto cardDto);
    void updateCardState(CardDto cardDto);

    void updateCardUser(CardDto cardDto, User user);

    Card findCardWithIdByNumber(String number);
    CardDto findCardByNumber(String number);

    List<CardDto> findAllCards();

    List<CardDto> findAllFreeCardsByAmount(Double amount);

    List<CardDto> findAllCardsByUser(User user);



}
