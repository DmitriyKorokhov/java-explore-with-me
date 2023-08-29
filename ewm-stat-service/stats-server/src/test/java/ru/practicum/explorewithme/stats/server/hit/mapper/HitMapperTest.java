package ru.practicum.explorewithme.stats.server.hit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class HitMapperTest {
    private HitDto hitDto;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime time = LocalDateTime.parse("2022-09-06 11:00:00", formatter);

    @BeforeEach
    private void beforeEach() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp(time)
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
}