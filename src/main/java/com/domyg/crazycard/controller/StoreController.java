package com.domyg.crazycard.controller;

import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.dto.UserDto;
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

@Controller
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    public StoreController(StoreService storeService,
                           UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    @GetMapping("/admin/stores")
    public String showStore(Model model) {
        StoreDto storeDto = new StoreDto();
        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);
        model.addAttribute("storeDto", storeDto);
        return "/admin/stores";

    }

    @PostMapping("/admin/stores/add")
    public String addStore(@Valid @ModelAttribute("storeDto") StoreDto storeDto,
                           BindingResult result,
                           Model model) {
        Store existingStore = storeService.findByName(storeDto.getName());

        if(existingStore != null && existingStore.getName() != null
        && !existingStore.getName().isEmpty()) {
            result.rejectValue("name", null,
                "Negozio Esistente");
        }
        if(storeDto.getName().equals("") || storeDto.getLocality().equals("")) {
            return "redirect:/admin/stores?blank";
        }
        if(result.hasErrors()){
            model.addAttribute("storeDto", storeDto);
            return "redirect:/admin/stores?exists";
        }

        storeService.saveStore(storeDto);
        return "redirect:/admin/stores?success";
    }

    @PostMapping("/admin/stores/authorize")
    public String updateStoreState(@RequestParam String storeName,
                                   Model model) {
        Store store = storeService.findByName(storeName);
        StoreDto storeDto = new StoreDto();
        storeDto.setName(store.getName());
        storeDto.setLocality(store.getLocality());
        storeDto.setAuthorized(store.getAuthorized());
        storeDto.setId(store.getId());
        if(storeDto.getAuthorized())
            storeDto.setAuthorized(false);
        else
            storeDto.setAuthorized(true);
        storeService.updateStoreAuthorization(storeDto);
        List<StoreDto> storesDto = storeService.findAll();
        model.addAttribute("stores", storesDto);
        return "redirect:/admin/stores?updated";
    }

    @PostMapping("/admin/stores/remove")
    public String removeStore(@RequestParam String storeNameRemove,
                              Model mode) {
        // Poichè sussiste un vincolo di Foreign Key tra la Tabella degli stores
        // e la Tabella degli utenti, per poter rimuovere uno Store è innanzitutto
        // necessario rimuovere questo dalle entity utente.

        Store store = storeService.findByName(storeNameRemove);
        if(store == null) {
            return "redirect:/admin/stores?error";
        }
        List<User> userList = store.getUsers();
        UserDto userDto = new UserDto();
        for (User user: userList) {
            userDto.setEmail(user.getEmail());
            userService.removeUserStore(userDto);
        }

        // A questo punto, andiamo a eliminare tutti gli utenti dall'attributo di
        // tipo lista che memorizza gli utenti dell'entity Store
        List<User> emptyList = new ArrayList<>();
        store.setUsers(emptyList);
        StoreDto storeDto = new StoreDto();
        storeDto.setName(store.getName());
        storeDto.setLocality(store.getLocality());
        storeDto.setAuthorized(false);
        storeService.updateStoreUsers(storeDto, emptyList);
        storeService.removeStore(storeDto);
        return "redirect:/admin/stores?successremove";
    }


}
