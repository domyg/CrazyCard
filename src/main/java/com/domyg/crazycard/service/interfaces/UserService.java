package com.domyg.crazycard.service.interfaces;


import com.domyg.crazycard.dto.UserDto;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;

import java.util.List;

public interface UserService {
    void saveUserAdmin(UserDto userDto, String roleName);
    void saveUserCustomer(UserDto userDto);

    void removeUserStore(UserDto userDto);
    void removeUserRoles(User user);

    void updateUserStore(User user, Store store);

    void removeUser(User user);
    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}