package com.domyg.crazycard.service.classes;

import com.domyg.crazycard.dto.UserDto;
import com.domyg.crazycard.model.Role;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.repository.RoleRepository;
import com.domyg.crazycard.repository.UserRepository;
import com.domyg.crazycard.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUserAdmin(UserDto userDto, String roleName) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // Codifica della Password con Spring Security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        String roleNamePartial = roleName.toUpperCase();
        Role role = roleRepository.findByName("ROLE_" + roleNamePartial);
        /*
        if(role == null){
            role = checkRoleExist();
        }

         */
        user.setRoles(Arrays.asList(role));
        if(userDto.getStore() != null)
            user.setStore(userDto.getStore());
        userRepository.save(user);
    }

    @Override
    public void saveUserCustomer(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // Codifica della Password con Spring Security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_CUSTOMER");
        /*
        if(role == null){
            role = checkRoleExist();
        }

         */
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public void updateUserStore(User user, Store store) {
        user.setStore(store);
        userRepository.save(user);
    }

    @Override
    public void removeUserStore(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        user.setStore(null);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Il metodo findAllUsers utilizza userRepository per fare una query al database e ne mappa il risultato in
    // uno stream di oggetti userDto che viene poi convertito, tramite collect, in una lista.
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public void removeUserRoles(User user) {
        List<Role> emptyList = new ArrayList<>();
        user.setRoles(emptyList);
        userRepository.save(user);
    }

    public void removeUser(User user) {
        userRepository.delete(user);
    }



    // Con questo metodo vado a creare un oggetto userDto contenente tutte le informazioni dell'oggetto user
    // necessarie a costruire l'oggetto DTO.
    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    /*
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

     */
}