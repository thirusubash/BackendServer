package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountType;
    private String accountNumber;
    private String bankName;
    private String ifsc;
    private boolean primaryAccount=false;
    private String status="NOT_VERIFIED";
    @ManyToOne
    @JsonIgnoreProperties("bankAccounts")
    private Company company;
}
