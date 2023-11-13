package tech.aluve.calendar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.repository.UserRepository;
import tech.aluve.calendar.security.JwtToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.naming.AuthenticationException;

public class PasswordResetServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtToken token;

    private PasswordResetServiceImpl passwordResetService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        passwordResetService = new PasswordResetServiceImpl(passwordEncoder, userRepository, token, emailService);
    }

    @Test
    public void testAuthenticateUser_Success() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        User user = new User();
        user.setEmail("samsondev@gmail.com");

        Mockito.when(userRepository.existsByEmail("samsondev@gmail.com")).thenReturn(true);
        Mockito.when(userRepository.findByEmail("samsondev@gmail.com")).thenReturn(user);
        Mockito.when(token.createToken(user)).thenReturn("mockToken");

        passwordResetService.authenticateUser("samsondev@gmail.com", request);

        // Add assertions here to check the response code and message
    }

    @Test
    public void testAuthenticateUser_UserNotFound() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        passwordResetService.authenticateUser("nonexistent@example.com", request);

        // Add assertions here to check the response code and message
    }

    @Test
    public void testValidateToken_ValidToken() throws AuthenticationException {
        User user = new User();
        String validToken = token.createToken(user);

        Mockito.when(token.validateClaims(validToken)).thenReturn(true);

        passwordResetService.validateToken(validToken);

        // Add assertions here to check the response code and message
    }

    @Test
    public void testValidateToken_ExpiredToken() throws AuthenticationException {
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjY4ODExOTU2Njl9.Q6DXz7arCUEs0m4wT6REuY85NTSEfcP6Wvmq-a-OfnQ";

        Mockito.when(token.validateClaims(expiredToken)).thenReturn(false);

        passwordResetService.validateToken(expiredToken);

        // Add assertions here to check the response code and message
    }

    @Test
    public void testResetPassword_Success() {
        String newPassword = "samuel@Pass1";
        String confirmPassword = "samuel@Pass1";
        User user = new User();
        user.setEmail("samsondev@gmail.com");

        Mockito.when(userRepository.findByEmail("samsondev@gmail.com")).thenReturn(user);
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        passwordResetService.setUserEmail("samsondev@gmail.com");
        passwordResetService.resetPassword(newPassword, confirmPassword);

        // Add assertions here to check the response code and message
    }

    @Test
    public void testResetPassword_PasswordNotValid() {
        String newPassword = "weak";
        String confirmPassword = "weak";
        User user = new User();
        user.setEmail("samsondev@gmail.com");

        passwordResetService.setUserEmail("samsondev@gmail.com");
        passwordResetService.resetPassword(newPassword, confirmPassword);

        // Add assertions here to check the response code and message
    }
}
