package com.domyg.crazycard.service.classes;

import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.repository.RoleRepository;
import com.domyg.crazycard.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void removeUserFromRole(User user, Role role) {

        List<User> userList = role.getUsers();
        if(userList.contains(user)) {
            userList.remove(user);
        }
        role.setUsers(userList);

    }

}
