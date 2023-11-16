package com.capstone.demo.config.auth;

import com.capstone.demo.config.CorsConfig;
import com.capstone.demo.jwt.JwtAccessDeniedHandler;
import com.capstone.demo.jwt.JwtAuthenticationEntryPoint;
import com.capstone.demo.jwt.JwtAuthenticationFilter;
import com.capstone.demo.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] SWAGGER = {
            "/v3/api-docs",
            "/swagger-resources/**", "/configuration/security", "/webjars/**",
            "/swagger-ui.html", "/swagger/**", "/swagger-ui/**"};
    private final CorsConfig corsConfig;

    private final JwtProvider jwtProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(SWAGGER);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .cors().and()
//                .authorizeRequests()
////                .antMatchers("/swagger-ui/**", "/swagger-ui/index.html").permitAll()
//                .antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/threads/**", "/api/v1/posts/**", "/api/v1/users/**",
//                        "/api/v1/collections/**", "/api/v1/forums/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(corsConfig.corsFilter())
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .cors().and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/join", "/api/v1/users/login", "/api/v1/auth/join", "/api/v1/auth/login", "/api/v1/auth/reissue").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/threads/**", "/api/v1/posts/**", "/api/v1/users/**",
                        "/api/v1/collections/**", "/api/v1/forums/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(corsConfig.corsFilter())
                .apply(new JwtSecurityConfig(jwtProvider));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}