package ru.practicum.stats.server.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stats.dto.model.StatsDto;
import ru.practicum.stats.server.hit.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query("SELECT new ru.practicum.stats.dto.model.StatsDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN (:start) AND (:end) " +
            "AND (COALESCE(:uris, null) IS NULL OR h.uri IN (:uris)) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY 3 DESC ")
    List<StatsDto> findStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.stats.dto.model.StatsDto(h.app, h.uri, COUNT(distinct h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN (:start) AND (:end) " +
            "AND (COALESCE(:uris, null) IS NULL OR h.uri IN (:uris)) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY 3 DESC ")
    List<StatsDto> findUniqueStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);
}