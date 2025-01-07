package com.qorvia.accountservice.service.follow;

import com.qorvia.accountservice.model.Follow;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStats;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.repository.FollowRepository;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.OrganizerStatsRepository;
import com.qorvia.accountservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final OrganizerStatsRepository organizerStatsRepository;

    @Transactional
    public ResponseEntity<?> followAndUnfollowOrganizer(Long organizerId, Long userId) {
        UserInfo user = userRepository.findById(userId).orElse(null);
        Organizer organizer = organizerRepository.findById(organizerId).orElse(null);

        if (user == null || organizer == null) {
            return new ResponseEntity<>("User or Organizer not found", HttpStatus.NOT_FOUND);
        }

        Follow existingFollow = followRepository.findByUserAndOrganizer(user, organizer);

        try {
            if (existingFollow != null) {
                followRepository.delete(existingFollow);

                OrganizerStats organizerStats = organizerStatsRepository.findByOrganizer(organizer);
                organizerStats.setFollowersCount(organizerStats.getFollowersCount() - 1);
                organizerStatsRepository.save(organizerStats);
                return new ResponseEntity<>("Unfollowed successfully", HttpStatus.OK);
            } else {
                Follow follow = Follow.builder()
                        .user(user)
                        .organizer(organizer)
                        .followedAt(LocalDateTime.now())
                        .build();
                followRepository.save(follow);

                OrganizerStats organizerStats = organizerStatsRepository.findByOrganizer(organizer);
                organizerStats.setFollowersCount(organizerStats.getFollowersCount() + 1);
                organizerStatsRepository.save(organizerStats);
                return new ResponseEntity<>("Followed successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
