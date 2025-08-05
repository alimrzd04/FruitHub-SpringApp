package org.example.service;

import org.example.dto.RegisterDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository ;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String registerUser(RegisterDto registerDto){
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if(existingUser.isPresent()) return "Email already exists";

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        return "Registration completed";
    }

}
