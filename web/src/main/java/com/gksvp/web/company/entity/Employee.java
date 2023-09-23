package  com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gksvp.web.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;


    private String employeeCode;


    private Date hireDate;


    private String designation;

    private String Email;
    private String mobileNumber;

    @Column(nullable = false)
    private double salary;

    private boolean status = false;

    private Date dateOfBirth;
    private int gender;

    private String  nationality;
    private String  maritalStatus;

    private String  address;
    private String emergencyContact;
    private String employmentType;
    private String reportingTo;

    private String education;
    private String skills;
    private String certifications;
    private String role;
    private String backgroundCheck;
//    @OneToOne
//    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("employees")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "plant_id") // Specify the name of the foreign key column
    @JsonIgnoreProperties("employees")
    private Plant plant; // Link to the Plant entity
    private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

}
