package com.ege.readingisgood.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum UserRoles {
    CUSTOMER(List.of(Roles.CUSTOMER_ROLE),"CUSTOMER"),
    EMPLOYEE(List.of(Roles.EMPLOYEE_ROLE),"EMPLOYEE"),
    ADMIN(List.of(Roles.EMPLOYEE_ROLE, Roles.ADMIN_ROLE),"ADMIN");

    private List<String> roles;
    private String type;

    public static Optional<UserRoles> getByType(String type){
        return Arrays.stream(UserRoles.values()).filter(x->x.getType().equals(type)).findAny();
    }

    public static final class Roles {
        private Roles(){}
        public static final String CUSTOMER_ROLE = "ROLE_CUSTOMER";
        public static final String EMPLOYEE_ROLE = "ROLE_EMPLOYEE";
        public static final String ADMIN_ROLE = "ROLE_ADMIN";

    }

}
