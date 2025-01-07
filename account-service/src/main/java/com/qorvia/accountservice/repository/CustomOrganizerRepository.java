package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;

import java.util.List;

public interface CustomOrganizerRepository {
    List<Organizer> findApprovedOrganizersWithCriteria(String search, int limit, Long organizerId);

}
