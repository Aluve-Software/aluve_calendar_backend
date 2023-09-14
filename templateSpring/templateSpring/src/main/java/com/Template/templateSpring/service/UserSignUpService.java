//package com.Template.templateSpring.Service;
//
//import com.Template.templateSpring.Dto.UserSignUpDto;
//import com.Template.templateSpring.Entity.User;
//import com.Template.templateSpring.Repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@org.springframework.stereotype.Service
//public class UserSignUpService implements Service {
//
//    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public UserSignUpService(UserRepository userRepository,
//                           PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void saveUser(UserSignUpDto userDto) {
//        User user = new User();
//        user.setEmail(userDto.getEmail());
//        // encrypt the password using spring security
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        userRepository.save(user);
//    }
//
//    @Override
//    public User findUserByEmail(String userEmail) {
//        return userRepository.findByEmail(userEmail);
//    }
//
//    @Override
//    public List<UserSignUpDto> findAllUsers() {
//
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map((user) -> mapToUserDto(user))
//                .collect(Collectors.toList());
//    }
//
//    private UserSignUpDto mapToUserDto(User user){
//        UserSignUpDto userDto = new UserSignUpDto();
//        userDto.setEmail(user.getEmail());
//        return userDto;
//    }
//
//
//}
