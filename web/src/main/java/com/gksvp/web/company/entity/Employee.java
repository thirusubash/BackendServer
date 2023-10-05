package  com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String employeeCode;
    private String email;
    private String mobileNumber;

    @Column(nullable = false)
    private double salary;

    private boolean status = false;
    private LocalDate hireDate;
    private LocalDate dateOfBirth;
    private String gender;

    private String  nationality;
    private String  maritalStatus;

    private String  address;
    private String emergencyContact;
    private String employmentType;
    private String reportingTo;

    private String education;
    private String skills;
    private String certifications;
    private String backgroundCheck;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    @JsonIgnoreProperties({"companyAddresses","products","plants","employees","bankAccounts","suppliers","locations","groups","roles","designations","designation"})
    private Designation designation;



    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({"companyAddresses","products","plants","employees","bankAccounts","suppliers","locations","groups","roles","designations","company"})
    private Company company;

    @ManyToOne
    @JoinColumn(name = "plant_id") // Specify the name of the foreign key column
    @JsonIgnoreProperties({"companyAddresses","products","employees","bankAccounts","suppliers","locations","groups","roles","designations"})
    private Plant plant; // Link to the Plant entity
    private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
    @ManyToMany
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties({"companyAddresses","products","employees","bankAccounts","suppliers","locations","roles","groups","designations"}) // Assuming the Role entity has a list of employees
    private List<CompanyRole> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties({"companyAddresses","products","employees","bankAccounts","suppliers","locations","roles","groups","designations"}) // Assuming the Role entity has a list of employees
    private List<CompanyGroup> groups = new ArrayList<>();


}
