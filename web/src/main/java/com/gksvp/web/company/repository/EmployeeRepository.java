package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByCompany_Id(Long companyId, Pageable pageable);

    // Assuming you want to search by first name and last name
    Page<Employee> findByCompany_IdAndFirstNameContainsOrLastNameContains(
            Long companyId, String firstName, String lastName, Pageable pageable);

    Page<Employee> findByCompanyId(Long companyId, Pageable pageable);

    Page<Employee> findByCompanyIdAndFirstNameContainingOrCompanyIdAndLastNameContaining(Long companyId, String keyword, Long companyId1, String keyword1, Pageable pageable);
}


