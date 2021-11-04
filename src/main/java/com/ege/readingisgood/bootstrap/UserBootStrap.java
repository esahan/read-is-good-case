package com.ege.readingisgood.bootstrap;

import com.ege.readingisgood.domain.User;
import com.ege.readingisgood.domain.UserRoles;
import com.ege.readingisgood.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserBootStrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        final String adminEmail= "admin@readisgood.com";
        Optional<User> adminByEmail = userRepository.findByEmail(adminEmail);

        if(!adminByEmail.isPresent()) {
            User admin = User.builder()
                    .email(adminEmail)
                    .id(0)
                    .gender("M")
                    .name("admin")
                    .surname("mugen")
                    .password(encoder.encode("12345"))
                    .type(UserRoles.ADMIN.getType())
                    .roles(UserRoles.ADMIN.getRoles())
                    .build();
            userRepository.save(admin);
        }
    }
}
