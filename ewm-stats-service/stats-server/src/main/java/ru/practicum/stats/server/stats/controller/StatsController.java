package ru.practicum.stats.server.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.model.StatsDto;
import ru.practicum.stats.server.stats.service.StatsService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StatsDto> getStats(@RequestParam String start,
                                   @RequestParam String end,
                                   @RequestParam(defaultValue = "false") Boolean unique,
                                   @RequestParam(required = false) List<String> uris) {
        log.info("Получение статистики по посещениям");
        return statsService.getStats(start, end, uris, unique);
    }
}