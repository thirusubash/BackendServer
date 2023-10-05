package com.gksvp.web.company.service;

// DefaultValues.java

import com.gksvp.web.company.entity.CompanyRole;
import com.gksvp.web.company.entity.CompanyGroup;
import com.gksvp.web.company.entity.Designation;

import java.util.Arrays;
import java.util.List;

public class DefaultValues {

    // Default Roles for Company
    public static final List<CompanyRole> DEFAULT_ROLES = Arrays.asList(
            new CompanyRole("admin", "Admin role with all permissions.",true),
            new CompanyRole("user", "Standard user with limited permissions.",true)
    );

    // Default Groups for Company
    public static final List<CompanyGroup> DEFAULT_GROUPS = Arrays.asList(
            new CompanyGroup("admin", "Group for administrators.",true),
            new CompanyGroup("user", "Group for regular users.", true)
    );

    // Default Designations for Company
    public static final List<Designation> DEFAULT_DESIGNATIONS = Arrays.asList(
            new Designation("Administrator", "Chief Executive Officer",true),
            new Designation("CEO", "Chief Executive Officer",true),
            new Designation("Manager", "Manager of a department or team.",true),
            new Designation("Staff", "Regular staff member.",true)
    );
}
