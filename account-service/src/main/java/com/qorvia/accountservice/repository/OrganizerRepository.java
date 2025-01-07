package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrganizerRepository extends JpaRepository<Organizer,Long>, CustomOrganizerRepository {
    Boolean existsByEmail(String email);

    Optional<Organizer> findByEmail(String email);

    Page<Organizer> findByOrganizationNameContainingIgnoreCase(String organizationName, Pageable pageable);

    Page<Organizer> findAllByRegistrationStatus(RegisterRequestStatus registerRequestStatus, Pageable pageable);

    Page<Organizer> findByOrganizationNameContainingIgnoreCaseAndRegistrationStatus(String organizationName, RegisterRequestStatus registerRequestStatus, Pageable pageable);

    Page<Organizer> findAllByStatusAndIdNotInAndRolesNot(OrganizerStatus status, Set<Long> ids, Roles role, Pageable pageable);

    Page<Organizer> findAllByStatusAndRolesNot(OrganizerStatus status, Roles role, Pageable pageable);

}