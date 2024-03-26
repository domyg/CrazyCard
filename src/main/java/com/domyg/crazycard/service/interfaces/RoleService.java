package com.domyg.crazycard.service.interfaces;

import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.User;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();

    Role findRoleByName(String roleName);

    void removeUserFromRole(User user, Role role);

}
