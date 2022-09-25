package com.springboot.security.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.springboot.security.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    EMPLOYEE(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, EMPLOYEE_READ, EMPLOYEE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, COURSE_WRITE, EMPLOYEE_READ, EMPLOYEE_WRITE));
    private final Set<ApplicationUserPermission> permissions;
    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions = permissions;

    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission ->new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
