package com.example.demo.dtos;

import com.example.demo.utils.LocalDateWithoutDay;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

public record UserDto(
        @JsonProperty("username")
        String username,
        @JsonProperty("emailAddress")
        String emailAddress,
        @JsonSerialize(using = LocalDateWithoutDay.Serializer.class)
        @JsonDeserialize(using = LocalDateWithoutDay.Deserializer.class)
        @JsonProperty("birthMonth")
        LocalDate birthMonth) {

    public static class Converter extends AbstractHttpMessageConverter<UserDto> {
        private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        private static final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        private static final String USERNAME_PARAM = "username";
        private static final String EMAIL_ADDRESS_PARAM = "emailAddress";
        private static final String BIRTH_MONTH_PARAM = "birthMonth";

        @Override
        protected boolean supports(Class<?> clazz) {
            return (UserDto.class == clazz);
        }

        @Override
        protected UserDto readInternal(Class<? extends UserDto> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            var values = formHttpMessageConverter.read(null, inputMessage);
            var usernameStr = getFirstAndRemove(values, USERNAME_PARAM);
            var emailStr = getFirstAndRemove(values, EMAIL_ADDRESS_PARAM);
            var birthMonthStr = getFirstAndRemove(values, BIRTH_MONTH_PARAM);
            return new UserDto(usernameStr, emailStr, birthMonthStr == null ? null : LocalDate.parse(birthMonthStr, FORMATTER));
        }

        private String getFirstAndRemove(MultiValueMap<String, String> values, String param) {
            var list = values.remove(param);
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.getFirst();
        }

        @Override
        protected void writeInternal(UserDto userDto, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            MultiValueMap<String, Object> values = new LinkedMultiValueMap<>();
            values.put(USERNAME_PARAM, List.of(userDto.username()));
            values.put(EMAIL_ADDRESS_PARAM, List.of(userDto.emailAddress()));
            values.put(BIRTH_MONTH_PARAM, List.of(userDto.birthMonth().format(FORMATTER)));
            formHttpMessageConverter.write(values, MediaType.APPLICATION_FORM_URLENCODED, outputMessage);
        }
    }

}
