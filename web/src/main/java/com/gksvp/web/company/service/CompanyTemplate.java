package com.gksvp.web.company.service;

import com.gksvp.web.company.entity.*;
import com.gksvp.web.company.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CompanyTemplate {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyRoleRepository roleRepository;

    @Autowired
    private CompanyGroupRepository groupRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public   Company createCompanyWithDefaults(Company companyData) {
        Company savedCompany = companyRepository.save(companyData);

        for (CompanyRole role : DefaultValues.DEFAULT_ROLES) {
            role.setCompany(savedCompany);
            roleRepository.save(role);
        }

        for (CompanyGroup group : DefaultValues.DEFAULT_GROUPS) {
            group.setCompany(savedCompany);
            groupRepository.save(group);
        }

        for (Designation designation : DefaultValues.DEFAULT_DESIGNATIONS) {
            designation.setCompany(savedCompany);
            designationRepository.save(designation);
        }

        CompanyRole adminRole = roleRepository.findByName("admin");
        CompanyGroup adminGroup = groupRepository.findByName("admin");
        Designation adminDesignation = designationRepository.findByName("Administrator");

        Employee adminEmployee = new Employee();
        adminEmployee.setFirstName("Administrator");
        adminEmployee.setLastName("Administrator");
        adminEmployee.setEmployeeCode("Default");
        adminEmployee.setEmail(savedCompany.getEmail());
        adminEmployee.setCompany(savedCompany);
        adminEmployee.setDesignation(adminDesignation);
        if (adminRole != null) {
            adminEmployee.getRoles().add(adminRole);
        }
        if (adminGroup != null) {
            adminEmployee.getGroups().add(adminGroup);
        }

        employeeRepository.save(adminEmployee);

        return savedCompany;
    }
}

