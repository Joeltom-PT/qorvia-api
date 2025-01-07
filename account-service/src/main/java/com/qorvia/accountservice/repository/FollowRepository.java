package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.Follow;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.user.UserInfo;
import io.netty.handler.codec.http2.Http2Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByUserAndOrganizer(UserInfo user, Organizer organizer);

    boolean existsByUserIdAndOrganizerId(Long userId, Long id);

    List<Follow> findByUserId(Long userId);

}
