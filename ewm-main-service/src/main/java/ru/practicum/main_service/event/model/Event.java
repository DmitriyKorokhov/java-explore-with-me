package ru.practicum.main_service.event.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "annotation", nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "paid", nullable = false)
    private Boolean paid = false;
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration = true;
}