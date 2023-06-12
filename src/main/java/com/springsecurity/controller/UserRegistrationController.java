package com.springsecurity.controller;

import com.springsecurity.dto.UserRegistrationDTO;
import com.springsecurity.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserServiceImpl service;

    @ModelAttribute("user")
    public UserRegistrationDTO getUserRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showUserRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDTO userDTO) {
        service.save(userDTO);
        return "redirect:/registration?success";
    }
}
