package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CustomOrganizerRepositoryImpl implements CustomOrganizerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Organizer> findApprovedOrganizersWithCriteria(String search, int limit, Long organizerId) {
        String jpql = "SELECT o FROM Organizer o JOIN o.organizerSettings s " +
                "WHERE o.organizationName LIKE :search " +
                "AND o.registrationStatus = 'APPROVED' " +
                "AND o.status = :status " +
                "AND o.verificationStatus = :verificationStatus " +
                "AND o.roles = :roles " +
                "AND s.isApprovalAllowed = true " +
                "AND o.id != :organizerId";

        TypedQuery<Organizer> query = entityManager.createQuery(jpql, Organizer.class);
        query.setParameter("search", "%" + search + "%");
        query.setParameter("status", OrganizerStatus.ACTIVE);
        query.setParameter("verificationStatus", VerificationStatus.VERIFIED);
        query.setParameter("roles", Roles.ORGANIZER);
        query.setParameter("organizerId", organizerId);
        query.setMaxResults(limit);

        return query.getResultList();
    }



}
