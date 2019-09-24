package com.angkorsuntrix.demosynctrix;

import com.angkorsuntrix.demosynctrix.entity.User;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import com.angkorsuntrix.demosynctrix.service.AuthenticationService;
import com.angkorsuntrix.demosynctrix.utils.WriteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class RegisterFilter extends AbstractAuthenticationProcessingFilter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository repository;

    RegisterFilter(String url, AuthenticationManager authManger, UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManger);
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        final User cred = new ObjectMapper().readValue(request.getInputStream(), User.class);
        final User user = repository.findByUsername(cred.getUsername());
        if (user == null) {
            final String password = cred.getPassword();
            cred.setPassword(passwordEncoder.encode(password));
            cred.setRole("USER");
            repository.save(cred);
            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(cred.getUsername(), password, Collections.emptyList()));
        }
        WriteResponse.writeErrorMessage(response, "user already exist!", HttpServletResponse.SC_CONFLICT);
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthenticationService.addToken(response, authResult.getName());
    }
}
