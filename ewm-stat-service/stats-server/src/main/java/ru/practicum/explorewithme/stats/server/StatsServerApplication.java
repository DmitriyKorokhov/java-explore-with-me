package ru.practicum.explorewithme.stats.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.practicum.explorewithme.stats.server.formatter.DateTimeFormatConfig;

@SpringBootApplication
@Import(DateTimeFormatConfig.class)
public class StatsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatsServerApplication.class, args);
    }

}