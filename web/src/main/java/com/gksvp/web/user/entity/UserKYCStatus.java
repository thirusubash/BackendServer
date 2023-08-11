package com.gksvp.web.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_kyc_status")
public class UserKYCStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kyc_status_id", nullable = false, unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kyc_info_id", referencedColumnName = "kyc_info_id")
    private UserKYCInfo kycInfo;

    @Column(name = "document_status", nullable = false)
    private String documentStatus;

    @Column(name = "reviewer")
    private String reviewer;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "comments")
    private String comments;

    // Other KYC status related attributes

}
