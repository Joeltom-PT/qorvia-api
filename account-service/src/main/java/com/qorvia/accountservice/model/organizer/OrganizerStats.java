package com.qorvia.accountservice.model.organizer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizer_stats")
public class OrganizerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @Column(name = "total_post_count", nullable = false)
    private Integer totalPostCount;

    @Column(name = "followers_count", nullable = false)
    private Integer followersCount;

    @Column(name = "total_events_count", nullable = false)
    private Integer totalEventsCount;
}
