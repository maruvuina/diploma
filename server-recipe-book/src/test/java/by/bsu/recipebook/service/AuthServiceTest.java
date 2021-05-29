package by.bsu.recipebook.service;

import by.bsu.recipebook.config.jwt.JwtProvider;
import by.bsu.recipebook.config.service.CustomUserDetails;
import by.bsu.recipebook.dto.auth.AuthenticationResponse;
import by.bsu.recipebook.dto.auth.LoginRequest;
import by.bsu.recipebook.dto.auth.RefreshTokenRequest;
import by.bsu.recipebook.dto.auth.RegisterRequest;
import by.bsu.recipebook.entity.NotificationEmail;
import by.bsu.recipebook.entity.RefreshToken;
import by.bsu.recipebook.entity.User;
import by.bsu.recipebook.exception.ServiceException;
import by.bsu.recipebook.mapper.UserMapper;
import by.bsu.recipebook.repository.RoleRepository;
import by.bsu.recipebook.repository.UserRepository;
import by.bsu.recipebook.repository.VerificationTokenRepository;
import by.bsu.recipebook.service.email.MailService;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@PowerMockIgnore({"*.*"})
@PrepareForTest({AuthService.class})
public class AuthServiceTest {
    private AutoCloseable closeable;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private MailService mailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private AuthService authService;

    private static final String PRIVATE_METHOD_GENERATE_VERIFICATION_TOKEN = "generateVerificationToken";

    private final String username = "email@gmail.com";

    private final String fullName = "John Smith";

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        authService = new AuthService(passwordEncoder, userRepository, roleRepository,
                userMapper, verificationTokenRepository, mailService,
                authenticationManager, jwtProvider, refreshTokenService);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @BeforeMethod(onlyForGroups = {"security"})
    public void setUpGSecurity() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void shouldSignup() throws Exception {
        User user = new User();
        user.setIdUser(1);
        user.setEmail(username);
        user.setFullName(fullName);
        RegisterRequest registerRequest =
                new RegisterRequest(username, null, fullName, null);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("pass");
        when(userMapper.toUser(any(RegisterRequest.class))).thenReturn(user);
        AuthService spy = PowerMockito.spy(authService);
        PowerMockito.when(spy, method(AuthService.class, PRIVATE_METHOD_GENERATE_VERIFICATION_TOKEN, User.class))
                .withArguments(any(User.class))
                .thenReturn("Some string");
        doNothing().when(mailService).sendMail(any(NotificationEmail.class));
        spy.signup(registerRequest);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getIdUser()).isEqualTo(1);
        assertThat(userArgumentCaptor.getValue().getFullName()).isEqualTo(fullName);
    }

    @Test(groups = {"security"})
    public void shouldGetCurrentUser() {
        User user = new User();
        user.setIdUser(1);
        user.setEmail(username);
        user.setFullName(fullName);
        CustomUserDetails customUserDetails = CustomUserDetails.fromUserToCustomUserDetails(user);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(userRepository.findByEmail(customUserDetails.getUsername())).thenReturn(user);
        User expected = authService.getCurrentUser();
        assertThat(expected.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void shouldRefreshToken() throws ServiceException {
        String authenticationToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        AuthenticationResponse expected = new AuthenticationResponse(authenticationToken, username, refreshToken, null, null, null);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken, username);
        when(refreshTokenService.validateRefreshToken(refreshToken)).thenReturn(true);
        when(jwtProvider.generateTokenWithUserName(username)).thenReturn(authenticationToken);
        AuthenticationResponse actual = authService.refreshToken(refreshTokenRequest);
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getRefreshToken()).isEqualTo(expected.getRefreshToken());
    }

    @Test(groups = {"security"})
    public void shouldIsLoggedIn() {
        when(authentication.isAuthenticated()).thenReturn(false);
        assertThat(authService.isLoggedIn()).isEqualTo(false);
    }

    @Test
    public void shouldLogin() {
        User user = new User();
        user.setIdUser(1);
        user.setEmail(username);
        user.setFullName(fullName);
        LoginRequest loginRequest = new LoginRequest(username, null);
        String authenticationToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        Authentication authenticate = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticate);
        when(jwtProvider.generateToken(authenticate)).thenReturn(authenticationToken);
        CustomUserDetails customUserDetails = CustomUserDetails.fromUserToCustomUserDetails(user);
        when(authenticate.getPrincipal()).thenReturn(customUserDetails);
        when(refreshTokenService.generateRefreshToken())
                .thenReturn(new RefreshToken(null, refreshToken, null));
        AuthenticationResponse expected =
                new AuthenticationResponse(authenticationToken, username, refreshToken, null, user.getIdUser(), null);
        AuthenticationResponse actual = authService.login(loginRequest);
        assertThat(expected.getUsername()).isEqualTo(actual.getUsername());
        assertThat(expected.getIdUser()).isEqualTo(actual.getIdUser());
    }
}
