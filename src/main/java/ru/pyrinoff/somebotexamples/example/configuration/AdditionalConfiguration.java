package ru.pyrinoff.somebotexamples.example.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"ru.pyrinoff.somebotexamples.example"})
@EnableJpaRepositories("ru.pyrinoff.somebotexamples.example.repository")
public class AdditionalConfiguration {

}
