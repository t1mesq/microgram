package com.attractor.microgram.service.impl.custom;

import com.attractor.microgram.model.User;
import com.attractor.microgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor

public class AuthUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).stream()
                .findFirst().orElseThrow();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(email)
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String email) {
        User user = userRepository.findByEmail(email).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("user with email " + email + "not found"));
        return List.of(new SimpleGrantedAuthority(user.getAuthority().getRole()));
    }
}
