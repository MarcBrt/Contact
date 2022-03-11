package fr.cnam.contact;


import fr.cnam.contact.entity.User;
import fr.cnam.contact.repository.UserRepository;
import fr.cnam.contact.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Optional<User> userAttempts = userRepository.findUserByUsername(username);
        if (userAttempts.isPresent()) {
            User user = userAttempts.get();
                if (passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
                    return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities());
                }
        }

        throw new BadCredentialsException("Password incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}