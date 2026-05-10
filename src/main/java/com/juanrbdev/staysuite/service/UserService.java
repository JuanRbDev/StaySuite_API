package com.juanrbdev.staysuite.service;

import com.juanrbdev.staysuite.dto.UpdateUserRequest;
import com.juanrbdev.staysuite.dto.UserResponse;
import com.juanrbdev.staysuite.entity.User;
import com.juanrbdev.staysuite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getMe(Authentication auth) {
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name()
        );
    }

    public UserResponse updateMe(Authentication auth, UpdateUserRequest request){

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(request.getUsername() != null && !request.getUsername().isBlank()){
            user.setUsername(request.getUsername());
        }

        if(request.getFirstName() != null && !request.getFirstName().isBlank()){
            user.setFirstName(request.getFirstName());
        }

        if(request.getLastName() != null && !request.getLastName().isBlank()){
            user.setLastName(request.getLastName());
        }

        if(request.getPhone() != null && !request.getPhone().isBlank()){
            user.setPhone(request.getPhone());
        }

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole().name()
        );
    }

    public void eliminarUser(Authentication auth){
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }



}
