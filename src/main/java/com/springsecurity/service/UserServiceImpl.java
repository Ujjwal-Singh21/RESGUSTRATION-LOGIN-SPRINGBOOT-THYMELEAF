package com.springsecurity.service;

import com.springsecurity.dto.UserRegistrationDTO;
import com.springsecurity.model.Role;
import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Override
    public User save(UserRegistrationDTO userDTO) {
//        User user = new User(userDTO.getFirstName(), userDTO.getLastName(),
//                userDTO.getEmail(), userDTO.getPassword(),
//                Arrays.asList(new Role("USER_ROLE")));

        User user = new User(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getEmail(), bcryptPasswordEncoder.encode(userDTO.getPassword()),
                Arrays.asList(new Role("USER_ROLE")));

        return repo.save(user);
    }

    // loadUserByUsername() from UserDetailsService(I)
    // Here username is nothing but e-mail from login page where user enters email-id and pswd and try to authenticate and Login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = repo.findByEmail(username);

       if(user == null) {
           throw new UsernameNotFoundException("Invalid Username and Password");
       }

       return new  org.springframework.security.core.userdetails.User(user.getEmail(),
               user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // mapRolesToAuthorities
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorityList = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorityList;
    }
}
