package com.example.demo.services;

import com.example.demo.daos.UserRepository;
import com.example.demo.dtos.UserDto;
import com.example.demo.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public void save(UserDto dto) {
        log.info("Saving {}", dto);
        userRepository.save(toModel(dto));
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getUsername(), user.getEmailAddress(), user.getBirthMonth());
    }

    private User toModel(UserDto dto) {
        return User.builder()
                .username(dto.username())
                .emailAddress(dto.emailAddress())
                .birthMonth(dto.birthMonth())
                .build();
    }
}
