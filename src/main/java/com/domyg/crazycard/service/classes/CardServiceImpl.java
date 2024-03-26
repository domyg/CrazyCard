package com.domyg.crazycard.service.classes;

import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.repository.CardRepository;
import com.domyg.crazycard.service.interfaces.CardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void saveCard(CardDto cardDto) {
        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setBalance(cardDto.getBalance());
        card.setState(cardDto.getState());
        card.setPin(cardDto.getPin());
        cardRepository.save(card);

    }

    @Override
    public void updateCardCredit(CardDto cardDto) {
        Card card = cardRepository.findByNumber(cardDto.getNumber());
        card.setBalance(cardDto.getBalance());
        cardRepository.save(card);
    }
    @Override
    public void updateCardState(CardDto cardDto) {
        Card card = cardRepository.findByNumber(cardDto.getNumber());
        card.setState(cardDto.getState());
        cardRepository.save(card);
    }

    @Override
    public void updateCardUser(CardDto cardDto, User user) {
        Card card = cardRepository.findByNumber(cardDto.getNumber());
        card.setUser(user);
        cardRepository.save(card);
    }

    public Card findCardWithIdByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public CardDto findCardByNumber(String number) {

        Card card = cardRepository.findByNumber(number);
        if(card != null) {
            return mapToCardDto(card);
        }
        else
            return null;
    }

    @Override
    public List<CardDto> findAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map((card) -> mapToCardDto(card))
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDto> findAllFreeCardsByAmount(Double amount) {
        List<Card> cards = cardRepository.findFreeCardsByBalance(amount);
        return cards.stream()
                .map((card) -> mapToCardDto(card))
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDto> findAllCardsByUser(User user) {
        List<Card> cards = cardRepository.findCardsByUser(user);
        if(cards.size() != 0) {
            return cards.stream()
                    .map((card) -> mapToCardDto(card))
                    .collect(Collectors.toList());
        }
        else {
            List<CardDto> emptyList = new ArrayList<>();
            return emptyList;
        }
    }

    private CardDto mapToCardDto(Card card){
        CardDto cardDto = new CardDto();
        cardDto.setBalance(card.getBalance());
        cardDto.setNumber(card.getNumber());
        cardDto.setState(card.getState());
        cardDto.setPin(card.getPin());
        if(card.getUser() != null)
            cardDto.setOwnerName(card.getUser().getName());
        else
            cardDto.setOwnerName("");
        return cardDto;
    }

}
