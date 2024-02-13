package ru.pec.china.beta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.pec.china.beta.service.PersonService;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProviderImpl(PersonService personService, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();

        UserDetails personDetails = personService.loadUserByUsername(login);
        String password = authentication.getCredentials().toString();

       if(!passwordEncoder.matches(password, personDetails.getPassword())) {
           throw new BadCredentialsException("Неправильный логин или пароль");
       }
        return new UsernamePasswordAuthenticationToken(personDetails, personDetails.getPassword(), personDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
