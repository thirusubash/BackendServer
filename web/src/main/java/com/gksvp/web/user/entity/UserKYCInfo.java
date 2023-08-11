package com.gksvp.web.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_kyc_info")
public class UserKYCInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kyc_info_id", nullable = false, unique = true)
    private Long kyc_info_id;

    @OneToOne(mappedBy = "kycInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserKYCStatus kycStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "issue_authority")
    private String issueAuthority;

    // Other KYC related attributes

}
