package com.juanrbdev.staysuite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String phone;
}