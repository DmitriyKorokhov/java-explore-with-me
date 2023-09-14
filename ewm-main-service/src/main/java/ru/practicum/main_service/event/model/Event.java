package ru.practicum.main_service.event.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Column(name = "title")
    @NotBlank(message = "The Title should not be empty")
    private String title;
    @Column(name = "annotation")
    @NotBlank(message = "The Annotation should not be empty")
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "description")
    @NotBlank(message = "The Description should not be empty")
    private String description;
    @Column(name = "paid")
    @NotNull(message = "The Paid should not be empty")
    private Boolean paid = false;
    @Column(name = "participant_limit")
    @NotNull(message = "The Participant Limit should not be empty")
    private Integer participantLimit;
    @Column(name = "event_date")
    @NotNull(message = "The EventDate Limit should not be empty")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Column(name = "created_on")
    @NotNull(message = "The CreatedOn Limit should not be empty")
    private LocalDateTime createdOn;
    @Column(name = "state")
    @NotNull(message = "The State Limit should not be empty")
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;
    @Column(name = "request_moderation")
    @NotNull(message = "The Request Moderation Limit should not be empty")
    private Boolean requestModeration = true;
}