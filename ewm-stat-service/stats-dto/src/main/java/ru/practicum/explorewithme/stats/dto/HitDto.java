package ru.practicum.explorewithme.stats.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class HitDto {
    private Long id;
    @NotBlank(message = "App у Hit не должно быть пустым")
    private String app;
    @NotBlank(message = "Uri у Hit не должно быть пустым")
    private String uri;
    @NotBlank(message = "Ip у Hit не должно быть пустым")
    private String ip;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "Неверный формат времени у Hit")
    private String timestamp;
}
