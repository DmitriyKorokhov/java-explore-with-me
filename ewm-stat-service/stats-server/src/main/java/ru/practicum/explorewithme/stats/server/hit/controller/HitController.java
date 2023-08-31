package ru.practicum.explorewithme.stats.server.hit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;
import ru.practicum.explorewithme.stats.server.hit.service.HitService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hit")
public class HitController {

    private final HitService hitService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hit addHit(@Validated @RequestBody HitDto hitDto) {
        log.info("Сохранение информации о том, что к эндпоинту был запрос");
        return hitService.addHit(hitDto);
    }
}