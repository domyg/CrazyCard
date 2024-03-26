package com.domyg.crazycard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] adminPages = {"/admin/**",
                "/users",
                "/admin/stores/**",
                "/register/admin",
                "/register/admin/save","/register",
                "/admin","/admin/card",
                "/admin/card",
                "/admin/card/state",
                "/admin/card/search",
                "/admin/card/**",
                "/admin/new-card",
                "/admin/new-card/**",
                "/admin/card/**"};

        String[] merchantPages = {"/merchant/card",
                "/merchant/**",
                "/merchant/card/**",
                "/merchant/card/addCredit",
                "/merchant/analysis",
                "merchant/analysis/**"};

        String[] customerPages = {"/customer/**",
                "/customer/analysis/**",
                "/card/buy",
                "/card/buy/**"};

        String[] allPages = {"../../static/css/**",
                "/register/customer",
                "/register/customer/**",
                "/index",
                "/",
                "/static/**",
                "/static/css/**",
                "/check_balance",
                "../favicon.ico"};

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(adminPages).hasRole("ADMIN")
                                .requestMatchers(merchantPages).hasRole("MERCHANT")
                                .requestMatchers((customerPages)).hasRole("CUSTOMER")
                                .requestMatchers(allPages).permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}