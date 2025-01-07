package com.qorvia.accountservice.dto.organizer.response;

import com.qorvia.accountservice.dto.organizer.OrganizerInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActiveOrganizersInfoResponse {
    private List<OrganizerInfo> organizers;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
