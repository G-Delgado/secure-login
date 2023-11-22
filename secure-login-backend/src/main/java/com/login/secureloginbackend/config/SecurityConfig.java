package com.login.secureloginbackend.config;

import com.login.secureloginbackend.config.JWT.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/h2-console/**").permitAll() // Para permitir el acceso a la consola H2
                                .requestMatchers("/auth","/auth/signup").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

/*    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/


    /*@Bean
    public AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthenticationManager(HandlerMappingIntrospector introspector){
        RequestMatcher permitAll = new AndRequestMatcher(new MvcRequestMatcher(introspector,"/auth"),
                new MvcRequestMatcher(introspector,"/auth/signup"), new MvcRequestMatcher(introspector,"/h2-console"));

        RequestMatcherDelegatingAuthorizationManager.Builder managerBuilder
                = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(permitAll,(context,other)-> new AuthorizationDecision(true));

        AuthorizationManager<HttpServletRequest> manager = managerBuilder.build();
        return (authentication, object) -> manager.check(authentication,object.getRequest());

    }*/
}
