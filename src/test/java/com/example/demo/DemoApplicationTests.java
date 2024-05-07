package com.example.demo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

class DemoApplicationTests {

	private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
			.appendPattern("yyyyMM")
			.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
			.toFormatter();

	@Test
	void contextLoads() {
		var date = LocalDate.parse("202401", FORMATTER);
		System.out.println(date);
	}

}
