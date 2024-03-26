package com.domyg.crazycard.controller;


import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.service.interfaces.StoreService;
import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.dto.TransactionDto;
import com.domyg.crazycard.model.Card;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.service.interfaces.CardService;
import com.domyg.crazycard.service.interfaces.TransactionService;
import com.domyg.crazycard.service.interfaces.UserService;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

// In questo Controller verranno svolte tutte le operazioni riguardanti la visualizzazione
// delle Transazioni al fine di svolgere attività di reportistica.

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    private final CardService cardService;

    private final StoreService storeService;

    public TransactionController(TransactionService transactionService,
                                 UserService userService,
                                 CardService cardService,
                                 StoreService storeService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.cardService = cardService;
        this.storeService = storeService;
    }


    @GetMapping("/merchant/analysis")
    public String showAnalysis() {
        return "/merchant/analysis";
    }

    @PostMapping("/merchant/analysis/on-date")
    public String onDate(@RequestParam String dateString,
                         @RequestParam String merchantName,
                         Model model) {

        User merchant = userService.findUserByEmail(merchantName);
        Store store = merchant.getStore();
        if(store == null) {
            return "redirect:/merchant/analysis?nostore";
        }
        Date date = Date.valueOf(dateString);
        List<TransactionDto> transactionsDto;
        transactionsDto =  transactionService.findTransactionsByDateAndStore(date, store);
        model.addAttribute("transactions", transactionsDto);
        return "/merchant/analysis";
    }

    @PostMapping("/merchant/analysis/on-range")
    public String onRange(@RequestParam String dateStartString,
                          @RequestParam String dateEndString,
                          @RequestParam String merchantName,
                          Model model) {
        Store store = userService.findUserByEmail(merchantName).getStore();
        if(store == null) {
            return "redirect:/merchant/analysis?nostore";
        }
        Date dateStart = Date.valueOf(dateStartString);
        Date dateEnd = Date.valueOf(dateEndString);
        List<TransactionDto> transactionsDto;
        transactionsDto = transactionService.findTransactionsByRangeAndStore(store, dateStart, dateEnd);
        model.addAttribute("transactions", transactionsDto);
        return "/merchant/analysis";
    }

    @PostMapping("/merchant/analysis/all")
    public String all(@RequestParam String merchantName,
                      Model model) {
        Store store = userService.findUserByEmail(merchantName).getStore();
        if(store == null) {
            return "redirect:/merchant/analysis?nostore";
        }
        List<TransactionDto> transactionsDto;
        transactionsDto = transactionService.findTransactionsByStore(store);
        model.addAttribute("transactions", transactionsDto);
        return "/merchant/analysis";
    }

    @GetMapping("/customer/analysis")
    public String showCustomerAnalysis(Model model) {

        String customerName = getAuthName();
        User customer = userService.findUserByEmail(customerName);
        List<CardDto> cardDtoList = cardService.findAllCardsByUser(customer);
        model.addAttribute("cards", cardDtoList);


        return "/customer/analysis";
    }


    @PostMapping("/customer/analysis/on-date")
    public String onDateCustomer(@RequestParam String customerName,
                         @RequestParam String cardNumber,
                         @RequestParam String dateString,
                         Model model) {

        User customer = userService.findUserByEmail(customerName);
        List<CardDto> cardDtoList = cardService.findAllCardsByUser(customer);
        if(cardNumber.equals("null")) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?noselection";
        }
        if(cardDtoList.size() == 0) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?nocardexists";
        }
        Card card = cardService.findCardWithIdByNumber(cardNumber);
        if(!(card.getUser().getEmail()).equals(customer.getEmail())) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?notowner";
        }
        Date date = Date.valueOf(dateString);
        List<TransactionDto> transactionsDto;
        transactionsDto =  transactionService.findTransactionsByDateAndCard(date, card);
        model.addAttribute("transactions", transactionsDto);
        model.addAttribute("cards", cardDtoList);
        return "/customer/analysis";
    }

    @PostMapping("/customer/analysis/on-range")
    public String onRangeCustomer(@RequestParam String customerName,
                          @RequestParam String cardNumber,
                          @RequestParam String dateStartString,
                          @RequestParam String dateEndString,
                          Model model) {

        User customer = userService.findUserByEmail(customerName);
        List<CardDto> cardDtoList = cardService.findAllCardsByUser(customer);
        if(cardNumber.equals("null")) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?noselection";
        }
        if(cardDtoList.size() == 0) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?nocardexists";
        }
        Card card = cardService.findCardWithIdByNumber(cardNumber);
        if(!(card.getUser().getEmail()).equals(customer.getEmail())) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?notowner";
        }
        Date dateStart = Date.valueOf(dateStartString);
        Date dateEnd = Date.valueOf(dateEndString);
        List<TransactionDto> transactionsDto;
        transactionsDto =  transactionService.findTransactionsByRangeAndCard(card, dateStart, dateEnd);
        model.addAttribute("transactions", transactionsDto);
        model.addAttribute("cards", cardDtoList);
        return "/customer/analysis";
    }


    @PostMapping("/customer/analysis/all")
    public String allCustomer(@RequestParam String customerName,
                      @RequestParam String cardNumber,
                      Model model) {

        User customer = userService.findUserByEmail(customerName);
        List<CardDto> cardDtoList = cardService.findAllCardsByUser(customer);
        if(cardNumber.equals("null")) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?noselection";
        }
        if(cardDtoList.size() == 0) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?nocardexists";
        }
        Card card = cardService.findCardWithIdByNumber(cardNumber);
        if(!(card.getUser().getEmail()).equals(customer.getEmail())) {
            model.addAttribute("cards", cardDtoList);
            return "redirect:/customer/analysis?notowner";
        }
        List<TransactionDto> transactionsDto;
        transactionsDto =  transactionService.findTransactionsByCard(card);
        model.addAttribute("transactions", transactionsDto);
        model.addAttribute("cards", cardDtoList);
        return "/customer/analysis";
    }

    @GetMapping("/admin/analysis")
    public String showAdminAnalysis(Model model) {
        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);
        return "/admin/analysis";
    }


    @GetMapping("/admin/analysis/card")
    public String showCardAnalysis(Model model) {
        List<CardDto> cardDtoList = cardService.findAllCards();
        HashMap<CardDto, Tuple> cardTransactionsMap = new HashMap<>();

        if(cardDtoList.isEmpty()) {
            return "redirect:/admin/analysis?nocards";
        }

        for(CardDto cardDto : cardDtoList) {
            Double tmpAmountTransactions;
            Double tmpAvgAmount = -0.0;
            int tmpNumberTransactions;

            tmpNumberTransactions = transactionService.countNegativeTransactionsByCard(cardDto);
            tmpAmountTransactions = transactionService.countNegativeTransactionsAmountByCard(cardDto);

            if(tmpAmountTransactions == null)
                tmpAmountTransactions = -0.0;

            if(tmpNumberTransactions > 0) {
                tmpAvgAmount = tmpAmountTransactions / tmpNumberTransactions;
                cardTransactionsMap.put(cardDto,
                        new Triplet<>(tmpNumberTransactions, tmpAmountTransactions * (-1), tmpAvgAmount * (-1)));
            }
            else if(tmpNumberTransactions == 0) {
                tmpAmountTransactions = 0.0;
                tmpAvgAmount = 0.0;
                cardTransactionsMap.put(cardDto,
                        new Triplet<>(tmpNumberTransactions, tmpAmountTransactions, tmpAvgAmount));

            }
        }
        model.addAttribute("cardtransmap", cardTransactionsMap);
        model.addAttribute("showCard", true);
        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);

        return "/admin/analysis";
    }

    @GetMapping("/admin/analysis/trans")
    public String showCardTransactions(@RequestParam String cardNumberSearch,
                                       Model model) {
        Card card = cardService.findCardWithIdByNumber(cardNumberSearch);
        if(card == null) {
            return "redirect:/admin/analysis?exists";
        }
        List<TransactionDto> transactionDtoList = transactionService.findTransactionsByCard(card);
        if(transactionDtoList.isEmpty()) {
            return "redirect:/admin/analysis?notransactionsoncard";
        }

        model.addAttribute("transactions", transactionDtoList);
        model.addAttribute("showTrans", true);

        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);
        return "/admin/analysis";
    }

    @GetMapping("/admin/analysis/store")
    public String showStoreTransactions(@RequestParam String storeNameSearch,
                                        Model model) {

        if(storeNameSearch.equals("null"))
            return "redirect:/admin/analysis?insertstore";

        Store store = storeService.findByName(storeNameSearch);

        if(store == null) {
            return "redirect:/admin/analysis?errorstore";
        }

        List<TransactionDto> transactionDtoList =
                transactionService.findTransactionsByStore(store);

        if(transactionDtoList.isEmpty())
            return "redirect:/admin/analysis?notransactionstore";

        model.addAttribute("transactionStore", transactionDtoList);
        model.addAttribute("showStore", true);

        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);

        return "/admin/analysis";
    }


    public String getAuthName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerName = "";
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                customerName = ((UserDetails) principal).getUsername();
            } else {
                customerName = principal.toString();
            }
        }
        return customerName;
    }
}
