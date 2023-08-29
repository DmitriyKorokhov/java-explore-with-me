package ru.practicum.explorewithme.stats.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class HitDto {
    private Long id;
    @NotBlank(message = "App у Hit не должно быть пустым")
    private String app;
    @NotBlank(message = "Uri у Hit не должно быть пустым")
    private String uri;
    @NotBlank(message = "Ip у Hit не должно быть пустым")
    private String ip;
    @NotNull(message = "Time stamp у Hit должен существовать")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
