package com.likorn_devaki.wordbook;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
*/
//@Configuration
//@ComponentScan(basePackages = { "com.likorn_devaki.wordbook" })
//@EnableWebSecurity
public class Config  {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        System.out.println();
        String h2 = encoder.encode("tsau");
        BCryptPasswordEncoder en = new BCryptPasswordEncoder(10);
        System.out.println(encoder.encode("tsau"));
        System.out.println(en.matches("tsau", "$2a$10$bjvPPQ7GBUo5nWfozIG1w.5khEBi.OJIpOFwnaz4MN1No8aiBbKPO"));
        System.out.println(encoder.matches("tsau", "$2a$10$bjvPPQ7GBUo5nWfozIG1w.5khEBi.OJIpOFwnaz4MN1No8aiBbKPO"));
    }

/*
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
 */
}