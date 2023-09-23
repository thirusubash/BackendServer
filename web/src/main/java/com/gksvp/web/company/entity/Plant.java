package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plants")
@EntityListeners(AuditingEntityListener.class)  // <- Add this for auditing
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String capacity;
    private String primaryProduct;

    private String machineryTypes;

    private int operationalHours;

    private String qualityStandards;

    private String safetyStandards;

    @Column(name = "environmental_impact")
    private String environmentalImpact;

    @Column(name = "raw_material_source")
    private String rawMaterialSource;

    private String email;
    private String contactNumber;

    private String storageCapacity;

    private Boolean status=true;

    @Column(name = "waste_disposal_system")
    private String wasteDisposalSystem;

    @Column(name = "polishing_agents")
    private String polishingAgents;

    @Column(name = "regulations_compliance")
    private String regulationsCompliance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"plants", "employees"})
    private Company company;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"plant","company"})
    private List<Employee> employees;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;

}
