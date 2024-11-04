package com.virtualpet.vpet.config;

import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static java.util.List.of;

@Configuration
@RequiredArgsConstructor
class UserDetailsConfig {

    private final UserRepository userRepository;


    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {

        return username -> userRepository.findByUsername(username)

                .map(UserDetailsConfig::getUserDetails);

    }

    private static User getUserDetails(com.virtualpet.vpet.model.User user) {

        List<GrantedAuthority> authorities = of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

    }

}
