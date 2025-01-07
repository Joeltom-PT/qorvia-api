package com.qorvia.accountservice.model;

import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.user.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "organizer_id"}))
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @Column(nullable = false)
    private LocalDateTime followedAt;

    @PrePersist
    protected void onCreate() {
        followedAt = LocalDateTime.now();
    }
}
