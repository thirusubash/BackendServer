package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Define custom query methods here if needed
}

