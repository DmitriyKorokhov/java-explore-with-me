package ru.practicum.stats.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HitDto {
    @NotBlank(message = "App у Hit не должно быть пустым")
    private String app;
    @NotBlank(message = "Uri у Hit не должно быть пустым")
    private String uri;
    @NotBlank(message = "Ip у Hit не должно быть пустым")
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}