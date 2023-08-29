package ru.practicum.explorewithme.stats.server.formatter;


import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.practicum.explorewithme.stats.server.exception.ValidationDateException;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class DateTimeFormatConfig implements WebMvcConfigurer {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        if (registrar == null) {
            throw new ValidationDateException("Дата должна быть задана");
        }
        try {
            registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DATE_FORMAT));
            registrar.registerFormatters(registry);
        } catch (DateTimeParseException e) {
            throw new ValidationDateException("Неверный формат даты");
        }
    }
}
