package ru.practicum.stats.server.hit.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hits", schema = "public")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app")
    @NotBlank(message = "App не должно быть пустым")
    private String app;
    @Column(name = "uri")
    @NotBlank(message = "Uri не должно быть пустым")
    private String uri;
    @Column(name = "ip")
    @NotBlank(message = "Ip не должно быть пустым")
    private String ip;
    @Column(name = "time_stamp")
    @NotNull(message = "Time stamp должен существовать")
    private LocalDateTime timestamp;
}