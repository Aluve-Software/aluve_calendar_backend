package tech.aluve.calendar.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.aluve.calendar.validator.EmailValidator;
import tech.aluve.calendar.validator.PasswordValidator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserSignUpServiceImplTest {

    @InjectMocks
    private UserSignUpServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @Test
    public void testSaveUser() throws Exception {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);

        // Create a UserSignUpDto with valid data for testing
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail("mzwilidev@gmail.com");
        userSignUpDto.setPassword("password123");
        userSignUpDto.setConfirmationPassword("password123");
        User user = new User();
        user.setEmail("mzwilidev@gmail.com");
        user.setPassword(passwordEncoder.encode("password123"));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        // Mock userRepository.existsByEmail method to return false
        when(userRepository.existsByEmail(userSignUpDto.getEmail())).thenReturn(false);
        when(!emailValidator.validateEmail(userSignUpDto) || emailValidator.emailNull(userSignUpDto)).thenReturn(false);
        when(!passwordValidator.validPassword(userSignUpDto) || !passwordValidator.matchingPassword(userSignUpDto)
                || passwordValidator.nullPassword(userSignUpDto)).thenReturn(false);

        // Mock the async behavior of emailService.sendEmail
        // Mock the async behavior of emailService.sendEmail
        CompletableFuture<Void> emailServiceFuture = new CompletableFuture<>();
        doAnswer(invocation -> {
            emailServiceFuture.complete(null);
            return null;
        }).when(emailService).sendEmail(any(SimpleMailMessage.class));


        // Call the method to be tested
        userService.saveUser(userSignUpDto);

        // Wait for the asynchronous emailService.sendEmail to complete
        emailServiceFuture.get(5, TimeUnit.SECONDS);

        // Verify that the user was saved in the repository
        verify(userRepository).save(any(User.class));

        // Verify that emailService.sendEmail was called once with any SimpleMailMessage
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));

        // Verify that responseCode and responseMessage are set correctly
        assertEquals("R00", userService.getResponseCode());
        assertEquals("Successfully registered!", userService.getResponseMessage());
    }
}
