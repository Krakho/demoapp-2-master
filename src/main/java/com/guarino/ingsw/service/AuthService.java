package com.guarino.ingsw.service;

import com.guarino.ingsw.config.JwtTokenUtil;
import com.guarino.ingsw.dto.AuthenticationResponse;
import com.guarino.ingsw.dto.LoginRequest;
import com.guarino.ingsw.dto.RegisterRequest;
import com.guarino.ingsw.exceptions.DemoAppException;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.repository.UserRepository;
import com.guarino.ingsw.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Transactional
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    MailService mailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Map<String, Boolean> tokenBlackList;

    public void signup(RegisterRequest registerRequest) {
        if (isBlank(registerRequest.username)) {
            throw new DemoAppException("Username is required");
        }
        if (isBlank(registerRequest.password)) {
            throw new DemoAppException("Password is required");
        }
        if (isBlank(registerRequest.email)) {
            throw new DemoAppException("Email is required");
        }
        User user = new User();
        user.setUsername(registerRequest.username);
        user.setEmail(registerRequest.email);
        user.setPassword(passwordEncoder.encode(registerRequest.password));
        user.setCreated(Instant.now());
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        User principal = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenUtil.generateToken((UserDetails) authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusMillis(jwtTokenUtil.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public void invalidateToken(String token) {
        jwtTokenUtil.blacklistToken(token);
    }
}
