package com.gksvp.web.repository.motor;

import com.gksvp.web.model.motor.Motor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorRepository extends JpaRepository<Motor, Long> {
    // You can add custom methods for specific queries or operations related to
    // motors
    // Spring Data JPA will provide the implementation based on method names or
    // annotations
}