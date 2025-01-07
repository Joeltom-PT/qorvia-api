package com.qorvia.accountservice.model.organizer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizer_settings")
public class OrganizerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private Organizer organizer;

    @Column(name = "is_approval_allowed", nullable = false)
    private boolean isApprovalAllowed = false;

}
