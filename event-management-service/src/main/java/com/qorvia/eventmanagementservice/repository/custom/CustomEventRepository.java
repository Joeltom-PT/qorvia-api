package com.qorvia.eventmanagementservice.repository.custom;

import com.qorvia.eventmanagementservice.model.AdminApprovalStatus;
import com.qorvia.eventmanagementservice.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomEventRepository {
    Page<Event> findAllApprovedEvents(String eventState, Boolean isOnline, String categoryId,
                                      AdminApprovalStatus approvalStatus, String search, Pageable pageable);

    Page<Event> findAllEvents(String search, Boolean isOnline, String categoryId, String organizerId, String date, Pageable pageable);

}
