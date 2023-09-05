package ru.practicum.events.event.model;

import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.model.location.Location;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event { // События
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation")
    private String annotation; // example: Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории Краткое описание
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Transient
    private Long confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @Column(name = "created_on")
    private LocalDateTime createdOn; // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "description")
    private String description; //Полное описание события
    @Column(name = "event_date")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator; //Пользователь (краткая информация)
    @Embedded
    private Location location; //Широта и долгота места проведения события
    @Column(name = "paid")
    private boolean paid; // Нужно ли оплачивать участие
    @Column(name = "participant_limit")
    private int participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "published_on")
    private LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "request_moderation")
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    @Enumerated(EnumType.STRING)
    private EventState state; // example: PUBLISHED, Список состояний жизненного цикла события
    @Column(name = "title", unique = true)
    private String title; // example: Знаменитое шоу 'Летающая кукуруза' Заголовок
    @Transient
    private Long views; // Количество просмотров события
}