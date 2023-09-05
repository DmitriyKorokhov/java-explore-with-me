package ru.practicum.events.compilation.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Value
public class UpdateCompilationRequest { //Изменение информации о подборке событий. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
    private List<Long> events; //Список id событий подборки для полной замены текущего списка
    private Boolean pinned; //Закреплена ли подборка на главной странице сайта
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для заголовка подборки - 50, минимальное - 1")
    private String title; // Заголовок подборки
}