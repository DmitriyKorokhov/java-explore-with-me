package ru.practicum.explorewithme.stats.server.stats.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explorewithme.stats.dto.StatsDto;
import ru.practicum.explorewithme.stats.server.exception.ValidationDateException;
import ru.practicum.explorewithme.stats.server.hit.storage.HitRepository;
import ru.practicum.explorewithme.stats.server.formatter.DateFormatter;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsServiceImplTest {
    @Autowired
    HitRepository hitRepository;
    @Autowired
    StatsServiceImpl statsService;
    private List<String> uris = Arrays.asList("/events/1");
    private final String start = "2019-09-06 11:00:00";
    private final String end = "2030-09-06 11:00:00";
    private final String badEnd = "1987-09-06 11:00:00";
    private final String badStart = "2045-09-06 11:00:00";

    @BeforeEach
    private void beforeEach() {
        Hit hit1 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", DateFormatter.formatDate("2022-09-06 11:00:00")));
        Hit hit2 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", DateFormatter.formatDate("2022-09-07 12:00:00")));
        Hit hit3 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", DateFormatter.formatDate("2022-09-08 13:00:00")));
        Hit hit4 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.100.100", DateFormatter.formatDate("2023-03-03 07:00:00")));
        Hit hit5 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/56", "192.163.120.120", DateFormatter.formatDate("2023-03-06 09:00:00")));

    }

    @Test
    void getStatsNotListUrisAndUniqueTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(4L)
                .build();
        StatsDto statsDto2 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .hits(1L)
                .build();
        int size = 2;
        List<StatsDto> stats = statsService.getStats(start, end, null, false);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
        assertThat(stats.contains(statsDto2)).isTrue();
    }

    @Test
    void getStatsListUrisAndUniqueTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(4L)
                .build();
        int size = 1;
        List<StatsDto> stats = statsService.getStats(start, end, uris, false);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
    }

    @Test
    void getStatsListUrisAndUniqueTrueTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
        int size = 1;
        List<StatsDto> stats = statsService.getStats(start, end, uris, true);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
    }

    @Test
    void getStatsNotListUrisAndUniqueTrueTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
        StatsDto statsDto2 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .hits(1L)
                .build();
        int size = 2;
        List<StatsDto> stats = statsService.getStats(start, end, null, true);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
    }

    @Test
    void getStatsBadDateTest() {
        assertThrows(ValidationDateException.class,
                () -> statsService.getStats(badStart, badEnd, uris, true));
    }
}