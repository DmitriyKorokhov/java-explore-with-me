package ru.practicum.stats.server.hit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.server.hit.model.Hit;
import ru.practicum.stats.server.hit.service.HitService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hit")
public class HitController {

    private final HitService hitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hit addHit(@Valid @RequestBody HitDto endpointHit) {
        log.info("Сохранение информации о том, что к эндпоинту был запрос");
        return hitService.addHit(endpointHit);
    }
}