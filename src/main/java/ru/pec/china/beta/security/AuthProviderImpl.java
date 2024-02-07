package ru.pec.china.beta.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.pec.china.beta.service.PersonService;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonService personService;

    public AuthProviderImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();

        UserDetails personDetails = personService.loadUserByUsername(login);
        String password = authentication.getCredentials().toString();

       if(!personDetails.getPassword().equals(password)) {
           throw new BadCredentialsException("Неправильный логин или пароль");
       }
        return new UsernamePasswordAuthenticationToken(personDetails, password,
                Collections.emptyList());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
