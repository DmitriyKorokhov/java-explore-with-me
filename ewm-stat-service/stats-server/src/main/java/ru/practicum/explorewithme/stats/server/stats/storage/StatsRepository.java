package ru.practicum.explorewithme.stats.server.stats.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.practicum.explorewithme.stats.dto.StatsDto;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query("SELECT NEW ru.practicum.explorewithme.stats.dto.StatsDto(h.app, h.uri, COUNT (h.ip)) FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (h.ip) DESC")
    List<StatsDto> findByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT NEW ru.practicum.explorewithme.stats.dto.StatsDto(h.app, h.uri, COUNT (DISTINCT h.ip)) FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (h.ip) DESC")
    List<StatsDto> findByDateAndUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT NEW ru.practicum.explorewithme.stats.dto.StatsDto(h.app, h.uri, COUNT (h.ip)) FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (h.ip) DESC")
    List<StatsDto> findByDateAndUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT NEW ru.practicum.explorewithme.stats.dto.StatsDto(h.app, h.uri, COUNT (DISTINCT h.ip)) FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (h.ip) DESC")
    List<StatsDto> findByDateAndUrisWithUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);
}