package com.juanrbdev.staysuite.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String dni;
    private String firstName;
    private String lastName;
    private String phone;
}