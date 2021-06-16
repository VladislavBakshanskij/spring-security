package com.example.springsecurity.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); using http basic like default

        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        User.UserBuilder builder = User.withDefaultPasswordEncoder();
        // if using DB JdbcUserDetailsManager. PS use special tables
        return new InMemoryUserDetailsManager(
                builder
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .roles("USER")
                        .build(),
                builder
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
