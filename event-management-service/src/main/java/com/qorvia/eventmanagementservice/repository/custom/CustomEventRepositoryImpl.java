package com.qorvia.eventmanagementservice.repository.custom;

import com.qorvia.eventmanagementservice.model.AdminApprovalStatus;
import com.qorvia.eventmanagementservice.model.Event;
import com.qorvia.eventmanagementservice.model.EventState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomEventRepositoryImpl implements CustomEventRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Event> findAllApprovedEvents(String eventState, Boolean isOnline, String categoryId,
                                             AdminApprovalStatus approvalStatus, String search, Pageable pageable) {

        Query query = new Query();

        if (eventState != null) {
            query.addCriteria(Criteria.where("eventState").is(eventState));
        }

        if (categoryId != null) {
            query.addCriteria(Criteria.where("categoryId").is(categoryId));
        }

        if (approvalStatus != null) {
            query.addCriteria(Criteria.where("approvalStatus").is(approvalStatus));
        }

        if (isOnline != null) {
            query.addCriteria(Criteria.where("isOnline").is(isOnline));
        }

        if (search != null && !search.trim().isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("name").regex(search, "i"),
                    Criteria.where("description").regex(search, "i")
            ));
        }

        query.with(pageable);

        List<Event> events = mongoTemplate.find(query, Event.class);
        long total = mongoTemplate.count(query, Event.class);

        return new PageImpl<>(events, pageable, total);
    }


    @Override
    public Page<Event> findAllEvents(String search, Boolean isOnline, String categoryId, String organizerId, String date, Pageable pageable) {
        Query query = new Query();

        query.addCriteria(Criteria.where("approvalStatus").is(AdminApprovalStatus.ACCEPTED));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.addCriteria(Criteria.where("isBlocked").is(false));
        query.addCriteria(Criteria.where("eventState").is(EventState.PUBLISH));

        if (organizerId != null && !organizerId.trim().isEmpty()) {
            query.addCriteria(Criteria.where("organizerId").is(organizerId));
        }

        if (categoryId != null && !categoryId.trim().isEmpty()) {
            query.addCriteria(Criteria.where("categoryId").is(categoryId));
        }

        if (isOnline != null) {
            query.addCriteria(Criteria.where("isOnline").is(isOnline));
        }

        if (date != null && !date.trim().isEmpty()) {
            query.addCriteria(Criteria.where("timeSlots.date").is(date));
        }

        if (search != null && !search.trim().isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("name").regex(search, "i"),
                    Criteria.where("description").regex(search, "i")
            ));
        }

        query.with(pageable);

        List<Event> events = mongoTemplate.find(query, Event.class);
        long total = mongoTemplate.count(query, Event.class);

        return new PageImpl<>(events, pageable, total);
    }


}
