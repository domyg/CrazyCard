package com.domyg.crazycard.controller;

import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.dto.UserDto;
import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.service.interfaces.StoreService;
import com.domyg.crazycard.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private final UserService userService;
    private final StoreService storeService;

    public AuthController(UserService userService, StoreService storeService) {
        this.userService = userService;
        this.storeService = storeService;
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // Metodo che gestisce la richiesta proveniente dal form di registrazione
    @GetMapping("/register/admin")
    public String showRegistrationFormAdmin(Model model){
        // L'oggetto model viene creato per consentire al front-end di memorizzare i dati da passare al
        // back-end
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);
        return "register/admin";
    }

    // Gestione della richiesta Post per la registrazione di un utente da parte di un Admin
    @PostMapping("/register/admin/save")
    public String registrationAdmin(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               @RequestParam String roleName,
                               @RequestParam String storeName,
                               @Valid @ModelAttribute("store") StoreDto storeDto,
                               BindingResult resultStore,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null
                && !existingUser.getEmail().isEmpty()){
            List<Role> roles = userService.findUserByEmail(userDto.getEmail()).getRoles();
            List<String> roleNames = roles.stream()
                    .map((role) -> role.getName())
                    .toList();
            if(roleNames.contains("ROLE_" + roleName)) {
                result.rejectValue("email", null,
                        "Indirizzo email già utilizzato!");
            }
            else {
                result.rejectValue("email", null,
                        "Non è possibile registrare un utente con lo stesso" +
                                " indirizzo Email con cui opera nel sistema sotto altro ruolo!" +
                                "\n Consulta la sezione di Gestione Utenti per cambiare i Ruoli.");
            }
        }

        if(result.hasErrors()){
            List<StoreDto> storesDto = storeService.findAll();
            model.addAttribute("stores", storesDto);
            model.addAttribute("user", userDto);
            return "/register/admin";
        }
        if(resultStore.hasErrors()){
            List<StoreDto> storesDto = storeService.findAll();
            model.addAttribute("stores", storesDto);
            model.addAttribute("user", userDto);
            return "/register/admin";
        }

        if(roleName.equals("merchant")) {
            if(!storeName.equals("null")) {
                Store store = storeService.findByName(storeName);
                userDto.setStore(store);
            }
        }

        userService.saveUserAdmin(userDto, roleName);
        return "redirect:/register/admin?success";
    }

    @GetMapping("/register/customer")
    public String showRegistrationFormCustomer(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register/customer";
    }

    // Gestione della richiesta Post per la registrazione di un cliente

    @PostMapping("/register/customer/save")
    public String registrationCustomer(@Valid @ModelAttribute("user") UserDto userDto,
                                       BindingResult result,
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
                        "Indirizzo email già utilizzato!" +
                                "Effettua il Login!");
            }
            else {
                result.rejectValue("email", null,
                        "Non puoi utilizzare lo stesso indirizzo" +
                                "Email con cui operi nel sistema sotto altro ruolo!");
            }
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register/customer";
        }

        userService.saveUserCustomer(userDto);
        return "redirect:/register/customer?success";

    }


}