package com.juanrbdev.staysuite.controller;

import com.juanrbdev.staysuite.dto.UpdateUserRequest;
import com.juanrbdev.staysuite.dto.UserResponse;
import com.juanrbdev.staysuite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication auth) {
        return ResponseEntity.ok(userService.getMe(auth));
    };

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            Authentication auth,
            @RequestBody UpdateUserRequest request
    ){
        return ResponseEntity.ok(userService.updateMe(auth, request));
    };

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMe(Authentication auth){
        userService.eliminarUser(auth);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    };

}