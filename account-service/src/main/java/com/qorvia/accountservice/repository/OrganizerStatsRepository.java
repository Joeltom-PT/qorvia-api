package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerStatsRepository extends JpaRepository<OrganizerStats, Long> {
    OrganizerStats findByOrganizer(Organizer organizer);
}
