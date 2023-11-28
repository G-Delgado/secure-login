package com.login.secureloginbackend.config;

import com.login.secureloginbackend.config.JWT.JwtAuthFilter;
import com.login.secureloginbackend.service.security.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;


    /**
     * @param http - HttpSecurity object to configure the security
     * @return - SecurityFilterChain with the configuration of the security
     * @throws Exception - Exception if the configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        http.csrf(httpSecurityCsrfConfigurer ->
                httpSecurityCsrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/auth/login"),
                        mvcMatcherBuilder.pattern("/auth/signup"), mvcMatcherBuilder.pattern("/auth/**"),mvcMatcherBuilder.pattern("/auth/user/**"), mvcMatcherBuilder.pattern("/**"))
        );

        http.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin).disable());

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/signup")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/user/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll()
                        .anyRequest().authenticated()
        );

        http.sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    /*@Bean
    public AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthenticationManager(HandlerMappingIntrospector introspector){
        RequestMatcher permitAll = new AndRequestMatcher(new MvcRequestMatcher(introspector,"/auth/login"),
                new MvcRequestMatcher(introspector,"/auth/signup"), new MvcRequestMatcher(introspector,"/h2-console"));

        RequestMatcherDelegatingAuthorizationManager.Builder managerBuilder
                = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(permitAll,(context,other)-> new AuthorizationDecision(true));

        AuthorizationManager<HttpServletRequest> manager = managerBuilder.build();
        return (authentication, object) -> manager.check(authentication,object.getRequest());

    }*/
}
