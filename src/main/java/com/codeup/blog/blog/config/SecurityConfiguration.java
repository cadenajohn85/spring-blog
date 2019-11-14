package com.codeup.blog.blog.config;

import com.codeup.blog.blog.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http
                // Login Configuration
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/posts")
                .permitAll()

                // Logout Configuration
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")

                // Pages that require authentication
                .and()
                .authorizeRequests()
                .antMatchers("/posts/create",
                        "/posts/{id}/**",
                        "/posts/viewmine")
                .authenticated()
        ;
    }



}
