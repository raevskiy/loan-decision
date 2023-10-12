package com.example.loandecision.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService testUserDetailsService() {
        User basicUser = new User( "user@company.com", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        ));

        User managerUser = new User("manager@company.com", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_CLIENT_MANAGER")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser, managerUser
        ));
    }
}
