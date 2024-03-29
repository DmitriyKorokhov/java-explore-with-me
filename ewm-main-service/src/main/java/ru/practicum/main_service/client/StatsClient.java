package ru.practicum.main_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.model.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Slf4j
@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> postHit(HitDto hitDto) {
        log.info("Request to save information on the endpoint");
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Request for statistics");
        String path = getStatsPath(uris);
        Map<String, Object> parameters = getStatsParameters(start, end, uris, unique);
        return get(path, parameters);
    }

    private String getStatsPath(List<String> uris) {
        if (uris == null) {
            return "/stats?start={start}&end={end}&unique={unique}";
        } else {
            return "/stats?start={start}&end={end}&unique={unique}&uris={uris}";
        }
    }

    private Map<String, Object> getStatsParameters(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if (uris == null) {
            return Map.of(
                    "start", start.format(dateTimeFormatter),
                    "end", end.format(dateTimeFormatter),
                    "unique", unique
            );
        } else {
            return Map.of(
                    "start", start.format(dateTimeFormatter),
                    "end", end.format(dateTimeFormatter),
                    "unique", unique,
                    "uris", String.join(", ", uris)
            );
        }
    }
}