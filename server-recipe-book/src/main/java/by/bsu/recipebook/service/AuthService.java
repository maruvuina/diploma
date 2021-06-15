package by.bsu.recipebook.service;

import by.bsu.recipebook.mapper.UserMapper;
import by.bsu.recipebook.config.service.CustomUserDetails;
import by.bsu.recipebook.config.jwt.JwtProvider;
import by.bsu.recipebook.dto.auth.AuthenticationResponse;
import by.bsu.recipebook.dto.auth.LoginRequest;
import by.bsu.recipebook.dto.auth.RefreshTokenRequest;
import by.bsu.recipebook.dto.auth.RegisterRequest;
import by.bsu.recipebook.entity.NotificationEmail;
import by.bsu.recipebook.entity.Role;
import by.bsu.recipebook.entity.User;
import by.bsu.recipebook.entity.VerificationToken;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.repository.RoleRepository;
import by.bsu.recipebook.repository.UserRepository;
import by.bsu.recipebook.repository.VerificationTokenRepository;
import by.bsu.recipebook.service.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    @Transactional
    public boolean signup(RegisterRequest registerRequest) throws ServiceException {
        String email = registerRequest.getEmail();
        if (!userRepository.existsByEmail(email)) {
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ROLE_USER"));
            registerRequest.setRoles(roles);
            User user = userMapper.toUser(registerRequest);
            userRepository.save(user);
            String token = generateVerificationToken(user);
            String message = "Thank you for signing up to Recipe Book, please click on the below url to activate your account:\n " +
                    "http://localhost:4200/api/auth/accountVerification/" + token;
            mailService.sendMail(new NotificationEmail("Please Activate your account",
                    user.getEmail(), message));
        } else {
            throw new ServiceException("User with '" + email + "' already exists.");
        }
        return false;
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void verifyAccount(String token) throws ServiceException {
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(token)
                        .orElseThrow(() -> new ServiceException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(username);
        user.setAuthorise(true);
        user.setActive(true);
        userRepository.save(user);
    }

    @Transactional
    public boolean isUserActive(String username) {
        return userRepository.findByEmail(username).isActive();
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
        List<String> roles = customUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(customUserDetails.getUsername())
                .idUser(customUserDetails.getIdUser())
                .roles(roles)
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.
                getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByEmail(principal.getUsername());
    }

    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ServiceException {
        String requestToken = refreshTokenRequest.getRefreshToken();
        if (!refreshTokenService.validateRefreshToken(requestToken)) {
            throw new ServiceException("Invalid refresh Token");
        }
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(requestToken)
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
