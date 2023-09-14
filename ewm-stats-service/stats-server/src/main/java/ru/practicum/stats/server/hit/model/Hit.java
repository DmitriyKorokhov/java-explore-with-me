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
    @NotBlank(message = "The App should not be empty")
    private String app;
    @Column(name = "uri")
    @NotBlank(message = "The Uri should not be empty")
    private String uri;
    @Column(name = "ip")
    @NotBlank(message = "The Ip should not be empty")
    private String ip;
    @Column(name = "time_stamp")
    @NotNull(message = "The Time Stamp must exist")
    private LocalDateTime timestamp;
}