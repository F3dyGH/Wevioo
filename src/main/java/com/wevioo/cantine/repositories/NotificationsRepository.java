package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.Notifications;
import com.wevioo.cantine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findTop5ByUserIdOrderByDateTimeDesc(Long id);

    List<Notifications> findByUser(User user);

    @Query("SELECT n FROM Notifications n WHERE n.dateTime > :lastPollTimestamp")
    List<Notifications> findNewNotificationsSince(@Param("lastPollTimestamp") LocalDateTime lastPollTimestamp);
}
