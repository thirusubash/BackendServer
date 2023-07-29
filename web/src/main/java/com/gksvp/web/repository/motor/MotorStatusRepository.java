package com.gksvp.web.repository.motor;

import com.gksvp.web.model.motor.MotorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface MotorStatusRepository extends JpaRepository<MotorStatus, Long> {

    @Query("SELECT m FROM MotorStatus m WHERE m.motor.id = ?1 ORDER BY m.updateDateTime DESC")
    MotorStatus findTopByMotorIdOrderByUpdateDateTimeDesc(Long motorId);

    // You can add custom methods for specific queries or operations related to
    // motor statuses
    // Spring Data JPA will provide the implementation based on method names or
    // annotations
}
