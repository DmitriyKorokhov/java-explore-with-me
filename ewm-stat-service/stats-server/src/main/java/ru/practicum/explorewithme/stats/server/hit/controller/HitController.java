package ru.practicum.explorewithme.stats.server.hit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.service.HitService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hit")
public class HitController {

    private final HitService hitService;

    @PostMapping
    public void addHit(@Validated @RequestBody HitDto hitDto) {
        log.info("Сохранение информации о том, что к эндпоинту был запрос");
        hitService.addHit(hitDto);
    }
}