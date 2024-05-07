package com.example.demo.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

@Slf4j
public final class LocalDateWithoutDay {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyyMM")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();

    public static class Deserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            var text = jsonParser.getText();
            try {
                return LocalDate.parse(text, FORMATTER);
            } catch (DateTimeParseException ex) {
                log.error("Unable to parse {} as date without day. Expected format: yyyyMM", text, ex);
            }
            return null;
        }
    }

    public static class Serializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(localDate.format(FORMATTER));
        }
    }

    @Converter
    public static class EntityFieldConverter implements AttributeConverter<LocalDate, String> {

        @Override
        public String convertToDatabaseColumn(LocalDate localDate) {
            return localDate.format(FORMATTER);
        }

        @Override
        public LocalDate convertToEntityAttribute(String s) {
            return LocalDate.parse(s, FORMATTER);
        }
    }
}
