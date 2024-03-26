package com.domyg.crazycard.controller;

import com.domyg.crazycard.dto.CardDto;
import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.dto.UserDto;
import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.service.interfaces.CardService;
import com.domyg.crazycard.service.interfaces.RoleService;
import com.domyg.crazycard.service.interfaces.StoreService;
import com.domyg.crazycard.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final StoreService storeService;
    private final RoleService roleService;

    public UserController(UserService userService,
                          CardService cardService,
                          StoreService storeService,
                          RoleService roleService) {
        this.userService = userService;
        this.cardService = cardService;
        this.storeService = storeService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @PostMapping("/admin/users/management")
    public String manageUser(@RequestParam String email,
                            Model model) {
        User user = userService.findUserByEmail(email);
        List<String> roleNameList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleNameList.add(role.getName());
        }

        List<StoreDto> storeDtoList = new ArrayList<>();
        if(roleNameList.contains("ROLE_MERCHANT")) {
            storeDtoList = storeService.findAll();
        }
        model.addAttribute("user", user);
        model.addAttribute("rolelist", roleNameList);
        model.addAttribute("stores", storeDtoList);
        return "/admin/management";
    }

    @PostMapping("/admin/management/update/customer")
    public String updateCustomer(@RequestParam String customerEmail,
                                 @RequestParam String customerSelect) {

        User user = userService.findUserByEmail(customerEmail);

        if(customerSelect.equals("null")) {
            return "redirect:/admin/users?noupdatemade";
        }

        if(customerSelect.equals("disableCard")) {
            List<CardDto> cardDtoList = cardService.findAllCardsByUser(user);
            for (CardDto cardDto: cardDtoList) {
                cardDto.setState(false);
                cardService.updateCardState(cardDto);
            }
            return "redirect:/admin/users?customerdisabled";
        }

        if(customerSelect.equals("delete")) {
            List<String> roleNameList = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleNameList.add(role.getName());
            }
            if(roleNameList.contains("ROLE_ADMIN")) {
                return "redirect:/admin/users?unabledelete";
            }

            // Rimuovo da ogni carta dell'utente l'associazione con questo
            List<CardDto> cardDtoList = cardService.findAllCardsByUser(user);
            for (CardDto cardDto : cardDtoList) {
                cardService.updateCardUser(cardDto, null);
                cardDto.setState(false);
                cardService.updateCardState(cardDto);
            }
            // Imposto la lista dei ruoli dell'utente come lista vuota
            userService.removeUserRoles(user);

            // Rimuovo l'utente dalla Lista utenti del Ruolo CUSTOMER
            Role customerRole = roleService.findRoleByName("ROLE_CUSTOMER");
            roleService.removeUserFromRole(user, customerRole);
            userService.removeUser(user);

            return "redirect:/admin/users?deleted";

        }

        return "redirect:/admin/users/management";
    }

    @PostMapping("/admin/management/update/merchant")
    public String updateMerchant(@RequestParam String merchantEmail,
                                 @RequestParam String merchantSelect,
                                 @RequestParam String merchantStore) {

        User user = userService.findUserByEmail(merchantEmail);

        if(merchantSelect.equals("null")) {
            return "redirect:/admin/users?noupdatemade";
        }

        if(merchantSelect.equals("merchantdelete")) {

            List<String> roleNameList = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleNameList.add(role.getName());
            }
            if(roleNameList.contains("ROLE_ADMIN")) {
                return "redirect:/admin/users?unabledelete";
            }

            // Imposto la lista dei ruoli dell'utente come lista vuota
            userService.removeUserRoles(user);

            // Rimuovo da ogni ruolo l'utente da eliminare
            List<Role> roleList = roleService.findAllRoles();
            for(Role role : roleList) {
                roleService.removeUserFromRole(user, role);
            }

            // Per sicurezza, disabilito tutte le carte dell'utente qualora egli
            // ne avesse a causa di un doppio possedimento di ruolo che, di norma,
            // non sarebbe autorizzato dall'Azienda
            List<CardDto> cardDtoList = cardService.findAllCardsByUser(user);
            for (CardDto cardDto : cardDtoList) {
                cardService.updateCardUser(cardDto, null);
                cardDto.setState(false);
                cardService.updateCardState(cardDto);
            }

            userService.removeUser(user);
            return "redirect:/admin/users?deleted";
        }

        if(merchantSelect.equals("updatestore")) {

            Store newStore = storeService.findByName(merchantStore);

            if(newStore == null) {
                return "redirect:/admin/users?nostoreexists";
            }

            if(newStore.equals(user.getStore())) {
                return "redirect:/admin/users?noupdatemade";
            }

            userService.updateUserStore(user, newStore);

            return "redirect:/admin/users?storeupdated";
        }


        return "redirect:/admin/users?noupdatemade";
    }

}
