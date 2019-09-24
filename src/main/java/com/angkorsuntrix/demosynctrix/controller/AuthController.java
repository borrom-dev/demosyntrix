package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Role;
import com.angkorsuntrix.demosynctrix.entity.RoleName;
import com.angkorsuntrix.demosynctrix.entity.User;
import com.angkorsuntrix.demosynctrix.exception.AppException;
import com.angkorsuntrix.demosynctrix.payload.AccessToken;
import com.angkorsuntrix.demosynctrix.payload.ApiResponse;
import com.angkorsuntrix.demosynctrix.payload.LoginRequest;
import com.angkorsuntrix.demosynctrix.payload.SignUpRequest;
import com.angkorsuntrix.demosynctrix.repository.RoleRepository;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import com.angkorsuntrix.demosynctrix.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthController() {
    }

    @PostMapping("/sign_in")
    public HttpEntity authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccessToken accessToken = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/sign_up")
    public HttpEntity register(@Valid @RequestBody SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already token!"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email address already in use!"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(request.getUsername(),request.getPassword(),  request.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User register successfully"));
    }
}
