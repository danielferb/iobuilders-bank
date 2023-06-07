package com.iobuilders.bank.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.iobuilders.bank"})
public class AppConfiguration {

	@Value("#{'${spring.security.public-endpoints}'.split(';')}")
	private String[] authWhitelist;

	@Bean
	public ObjectMapper objectMapper() {
		return JsonMapper.builder().addModule(new JavaTimeModule()).build();
	}

	@Bean
	@Primary
	public Jackson2ObjectMapperBuilderCustomizer jsonMapperCustomizer() {
		return builder -> {
			builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		};
	}

}