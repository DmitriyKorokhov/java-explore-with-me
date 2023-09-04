package ru.practicum.explorewithme.stats.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "Invalid date format")
    private String timestamp;
}
