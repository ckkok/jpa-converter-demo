package com.example.demo;

import com.example.demo.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.List;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public UserDto.Converter userDtoConverter() {
        var converter = new UserDto.Converter();
        var mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED);
        converter.setSupportedMediaTypes(List.of(mediaType));
        log.info("Converter set up");
        return converter;
    }

}
