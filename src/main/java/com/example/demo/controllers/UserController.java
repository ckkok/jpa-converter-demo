package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String showUserAdd(Model model) {
        model.addAttribute("user", new UserDto(null, null, null));
        return "add-user";
    }

    @PostMapping("/user")
    public String addUser(@RequestBody UserDto user, Model model) {
        userService.save(user);
        return "redirect:/";
    }
}
