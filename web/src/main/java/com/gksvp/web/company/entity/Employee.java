package  com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String employeeCode;

    @Column(nullable = false)
    private Date hireDate;

    @Column(nullable = false)
    private double salary;

    @ManyToOne
    @JoinColumn(name = "company_id") // Specify the name of the foreign key column
    @JsonIgnoreProperties("employees")
    private Company company;

}
