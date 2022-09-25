package com.springboot.security.config;

public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");


    private final  String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;

    }

    public String getPermission() {
        return permission;

    }
}
