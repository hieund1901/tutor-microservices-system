package com.microservices.projectfinal.security;

import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Setter
@Service
public class AuthorDetailsService implements UserDetailsService {
    private String email;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return AuthorDetails.builder()
                .username(userId)
                .build();
    }


}
