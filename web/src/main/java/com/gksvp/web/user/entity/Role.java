package com.gksvp.web.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "users")
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties({"roles","addresses","kycInfoList","locations"})
    private List<User> users;
}
