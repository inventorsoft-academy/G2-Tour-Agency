package co.inventorsoft.touragency.security;

import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class {@code TourAgencyAuthenticationProvider} is a service that performs
 * authentication operations and provides access tokens for users.
 * */
@Component
public class TourAgencyAuthenticationProvider implements AuthenticationProvider {

    private List<User> users;

    @Autowired
    public TourAgencyAuthenticationProvider(AuthenticationService authenticationService) {
        this.users = authenticationService.getUsers();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> userOptional = users.stream()
                .filter(user -> user.match(username, password))
                .findFirst();

        if (!userOptional.isPresent()) {
            throw new BadCredentialsException("Failed authentication for credentials: " +
                    "username " + username + " AND password" + password.hashCode());
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = userOptional.get().isAdmin() ? "admin" : "user";

        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
