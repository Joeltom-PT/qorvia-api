package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerSettingsRepository extends JpaRepository<OrganizerSettings, Long> {
    Optional<OrganizerSettings> findByOrganizer(Organizer organizer);
}
