package ru.practicum.explorewithme.stats.server.hit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.exception.ValidationDateException;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HitMapperTest {
    private HitDto hitDto;

    @BeforeEach
    private void init() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
    }

    @Test
    @DisplayName("Тест преобразования hitDto -> hit")
    void toHitValidDataTest() {
        Hit hit = HitMapper.toHit(hitDto);
        assertThat(hit.getUri().equals(hitDto.getUri())).isTrue();
        assertThat(hit.getApp().equals(hitDto.getApp())).isTrue();
        assertThat(hit.getIp().equals(hitDto.getIp())).isTrue();
        assertThat(hit.getTimestamp().equals(LocalDateTime.of(2022, 9, 06, 11, 00, 00))).isTrue();
    }

    @Test
    @DisplayName("Тест преобразования hitDto -> hit, невалидная дата")
    void toHitNotValidDateTest() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("rrtt-09-@@ 11:00:aa")
                .build();
        assertThrows(ValidationDateException.class,
                () -> HitMapper.toHit(hitDto));

    }
}