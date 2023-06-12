package com.springsecurity.service;

import com.springsecurity.dto.UserRegistrationDTO;
import com.springsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

  User save(UserRegistrationDTO userRegistrationDTO);
}
