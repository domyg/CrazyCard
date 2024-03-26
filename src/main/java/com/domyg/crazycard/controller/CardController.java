
package com.domyg.crazycard.controller;


import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.dto.TransactionDto;
import com.domyg.crazycard.dto.UserDto;
import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.service.interfaces.CardService;
import com.domyg.crazycard.service.interfaces.TransactionService;
import com.domyg.crazycard.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;


@Controller
public class CardController {

    private final CardService cardService;
    private final TransactionService transactionService;
    private final UserService userService;

    public CardController(CardService cardService,
                          TransactionService transactionService,
                          UserService userService) {
        this.cardService = cardService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // Il seguente metodo riceve una richiesta get alla url /check_balance e ne
    // estrae il parametro dal tag di input denominato cardNumber.
    // questo parametro viene poi utilizzato tramite la l'oggetto cardService
    // per trovare il Credito Residuo della carta avente come numero proprio
    // cardNumber.
    // Il metodo, infine, aggiunge all'oggetto model gli attributi che devono
    // essere restituiti al front-end e ritorna la pagina dove questi saranno
    // visualizzati.

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/check_balance")
    public String checkCredit(@RequestParam("cardNumber") String cardNumber,
                              Model model) {
        CardDto cardDto = cardService.findCardByNumber(cardNumber);
        if(cardDto != null) {
            model.addAttribute("balance", "Credito: " + cardDto.getBalance());
            model.addAttribute("cardNumber", "Numero " + cardNumber);
        }
        else {
            model.addAttribute("invalidNumber", "Numero di carta non valido!");
        }
        return "/index";
    }

    @GetMapping("/admin/new-card")
    public String newCard() {
        return "/admin/new-card";
    }


    @PostMapping("/admin/new-card/save")
    public String saveNewCard(@RequestParam String amount,
                              @RequestParam String quantity,
                              Model model) {
        for (int i = 0; i < Integer.parseInt(quantity); i++){
            CardDto existingCard;
            String cardNumber;
            do {
                cardNumber = generateCardNumber();
                existingCard = cardService.findCardByNumber(cardNumber);
            } while (existingCard != null);
            CardDto cardDto = new CardDto();
            cardDto.setNumber(cardNumber);
            cardDto.setState(true);
            cardDto.setBalance(Double.parseDouble(amount));
            cardDto.setPin(generateCardPin());
            cardService.saveCard(cardDto);
        }
        model.addAttribute("cardNumber", quantity);
        return "redirect:/admin/new-card?success=" + quantity;
    }


    @GetMapping("/admin/card")
    public String cards(Model model){
        List<CardDto> cards = cardService.findAllCards();
        model.addAttribute("cards", cards);
        return "/admin/card";
    }

    @PostMapping("/admin/card/state")
    public String cardState(@RequestParam String number,
                            Model model) {
        CardDto cardDto = cardService.findCardByNumber(number);
        if(cardDto.getState())
            cardDto.setState(false);
        else
            cardDto.setState(true);
        cardService.updateCardState(cardDto);
        List<CardDto> cards = cardService.findAllCards();
        model.addAttribute("cards", cards);
        return "redirect:/admin/card";
    }

    @GetMapping("/admin/card/search")
    public String cardSearch(@RequestParam String cardNumberSearch,
                             Model model) {
        CardDto cardDto = cardService.findCardByNumber(cardNumberSearch);
        if(cardDto != null) {
            List<CardDto> cards = Arrays.asList(cardDto);
            model.addAttribute("cards", cards);
            return "/admin/card";
        }
        return "redirect:/admin/card?error";
    }

    @GetMapping("/merchant/card")
    public  String showCard() {
        return "/merchant/card";
    }

    @PostMapping("/merchant/card/removeCredit")
    public String removeCredit(@RequestParam String paymentAmount,
                               @RequestParam String removeNumber,
                               @RequestParam String removeUsername,
                               @RequestParam String removePin) {
        User user = userService.findUserByEmail(removeUsername);
        if(user.getStore() == null) {
            return "redirect:/merchant/card?nostore";
        }
        if(!user.getStore().getAuthorized()) {
            return "redirect:/merchant/card?unauthorized";
        }

        CardDto cardDto = cardService.findCardByNumber(removeNumber);
        if(cardDto == null)
            return "redirect:/merchant/card?removeexists";

        if(cardDto.getOwnerName() == "")
            return "redirect:/merchant/card?removenoowner";

        if(!cardDto.getState())
            return "redirect:/merchant/card?removedisabled";
        if(!cardDto.getPin().equals(removePin))
            return "redirect:/merchant/card?removepinerr";


        Double oldBalance = cardDto.getBalance();
        Double toRemove = Double.parseDouble(paymentAmount);
        Double newBalance = oldBalance - toRemove;
        if(newBalance < 0) {
            return "redirect:/merchant/card?removeerror";
        }
        cardDto.setBalance(newBalance);
        cardService.updateCardCredit(cardDto);
        User merchant = userService.findUserByEmail(removeUsername);
        saveTransaction(cardDto, -(toRemove), merchant.getStore());
        return "redirect:/merchant/card?removesuccess";
    }

    @PostMapping("/merchant/card/addCredit")
    public String addCredit(@RequestParam String addNumber,
                            @RequestParam String creditAmount,
                            @RequestParam String addUsername,
                            @RequestParam String addPin) {
        User user = userService.findUserByEmail(addUsername);
        if(user.getStore() == null) {
            return "redirect:/merchant/card?nostore";
        }

        if(!user.getStore().getAuthorized()) {
            return "redirect:/merchant/card?unauthorized";
        }
        CardDto cardDto = cardService.findCardByNumber(addNumber);
        if(cardDto == null)
            return "redirect:/merchant/card?addexists";

        if(cardDto.getOwnerName() == "")
            return "redirect:/merchant/card?addnoowner";

        if(!cardDto.getState())
            return "redirect:/merchant/card?adddisabled";
        if(!cardDto.getPin().equals(addPin))
            return "redirect:/merchant/card?addpinerr";

        User merchant = userService.findUserByEmail(addUsername);
        Double oldBalance = cardDto.getBalance();
        Double toAdd = Double.parseDouble(creditAmount);
        Double newBalance = oldBalance + toAdd;
        cardDto.setBalance(newBalance);
        cardService.updateCardCredit(cardDto);
        saveTransaction(cardDto, toAdd, merchant.getStore());
        return "redirect:/merchant/card?addsuccess";
    }

    @GetMapping("/merchant/assign-card")
    public String showAssignCard(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "/merchant/assign-card";
    }

    @PostMapping("/merchant/assign-card/login")
    public String assignNewCard(@RequestParam String email,
                                @RequestParam String password,
                                @RequestParam Double amount,
                                Model model) {
        User user = userService.findUserByEmail(email);
        if(user == null) {
            return "redirect:/merchant/assign-card?error";
        }
        if(!user.getRoles().contains("ROLE_CUSTOMER"))
            return "redirect:/merchant/assign-card?nocustomer";
        String encodedPassword = user.getPassword();
        if(!passwordEncoder.matches(password, encodedPassword)) {
            return "redirect:/merchant/assign-card?error";
        }

        List<CardDto> cardDtoList = cardService.findAllFreeCardsByAmount(amount);
        if(cardDtoList.size()==0) {
            return "redirect:/merchant/assign-card?nocard";
        }
        CardDto assignedCard = cardDtoList.get(0);
        cardService.updateCardUser(assignedCard, user);

        return "redirect:/merchant/assign-card?success";
    }

    @PostMapping("/merchant/assign-card/register")
    public String registerNewUser(@Valid @ModelAttribute("user") UserDto userDto,
                                  BindingResult result,
                                  @RequestParam Double regamount,
                                  Model model) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null
                && !existingUser.getEmail().isEmpty()) {
            List<Role> roles = userService.findUserByEmail(userDto.getEmail()).getRoles();
            List<String> roleNames = roles.stream()
                    .map((role) -> role.getName())
                    .toList();
            if(roleNames.contains("ROLE_CUSTOMER")) {
                result.rejectValue("email", null,
                        "Indirizzo email già utilizzato!");
            }
            else {
                result.rejectValue("email", null,
                        "Questo indirizzo email è già registrato nel sistema" +
                                "per operare sotto un altro ruolo.");
            }
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register/customer";
        }

        List<CardDto> cardDtoList = cardService.findAllFreeCardsByAmount(regamount);
        if(cardDtoList.size()==0) {
            model.addAttribute("user", userDto);
            return "redirect:/merchant/assign-card?regnocard";
        }
        CardDto assignedCard = cardDtoList.get(0);
        userService.saveUserCustomer(userDto);
        User user = userService.findUserByEmail(userDto.getEmail());
        cardService.updateCardUser(assignedCard, user);
        return "redirect:/merchant/assign-card?regsuccess";
    }


    public void saveTransaction(CardDto cardDto, Double amount, Store store) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setCardNumber(cardDto.getNumber());
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        transactionDto.setDate(date);
        transactionDto.setAmount(amount);
        transactionDto.setStoreName(store.getName());
        transactionService.saveTransaction(transactionDto);
    }
    public String generateCardNumber()
    {

        String digits = "0123456789";

        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {

            int index = (int)(digits.length() * Math.random());
            sb.append(digits.charAt(index));
        }

        return sb.toString();
    }

    public String generateCardPin()
    {

        String digits = "0123456789";

        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {

            int index = (int)(digits.length() * Math.random());
            sb.append(digits.charAt(index));
        }

        return sb.toString();
    }

}
