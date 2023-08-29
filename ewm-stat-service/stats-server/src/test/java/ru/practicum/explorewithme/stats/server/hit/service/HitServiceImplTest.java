package ru.practicum.explorewithme.stats.server.hit.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.storage.HitRepository;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class HitServiceImplTest {
    @Autowired
    HitServiceImpl hitService;
    @Autowired
    HitRepository hitRepository;
    private HitDto hitDto1;
    private HitDto hitDto2;
    private HitDto hitDto3;
    private Hit hit1;
    private Hit hit2;
    private Hit hit3;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime timeForHitDto1 = LocalDateTime.parse("2022-09-06 11:00:00", formatter);
    private LocalDateTime timeForHitDto2 = LocalDateTime.parse("2022-09-06 13:00:00", formatter);
    private LocalDateTime timeForHitDto3 = LocalDateTime.parse("2022-09-07 14:00:00", formatter);
    private LocalDateTime timeForHit1 = LocalDateTime.parse("2022-09-06 11:00:00", formatter);
    private LocalDateTime timeForHit2 = LocalDateTime.parse("2022-09-06 13:00:00", formatter);
    private LocalDateTime timeForHit3 = LocalDateTime.parse("2022-09-07 14:00:00", formatter);


    @BeforeEach
    private void beforeEach() {
        hitDto1 = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp(timeForHitDto1)
                .build();
        hitDto2 = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.101")
                .timestamp(timeForHitDto2)
                .build();
        hitDto3 = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .ip("192.163.12.99")
                .timestamp(timeForHitDto3)
                .build();
        hit1 = Hit.builder()
                .id(1L)
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp(timeForHit1)
                .build();
        hit2 = Hit.builder()
                .id(2L)
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.101")
                .timestamp(timeForHit2)
                .build();
        hit3 = Hit.builder()
                .id(3L)
                .app("ewm-main-service")
                .uri("/events/56")
                .ip("192.163.12.99")
                .timestamp(timeForHit3)
                .build();
    }

    @Test
    void addHitIntegrationTest() {
        final int size = 3;
        hitService.addHit(hitDto1);
        hitService.addHit(hitDto2);
        hitService.addHit(hitDto3);
        List<Hit> hits = hitRepository.findAll();
        assertThat(hits.size() == size).isTrue();
    }
}