package com.gksvp.web.motor.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotorStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Motor motor; // Reference to the Motor entity

    @CreationTimestamp
    private LocalDateTime updateDateTime;
    private String motorstatus;

    public Long getMotorId() {
        if (motor != null) {
            return motor.getId();
        }
        return null;
    }

    public String getMotorname() {
        if (motor != null) {
            return motor.getName();
        }
        return null;
    }
}
