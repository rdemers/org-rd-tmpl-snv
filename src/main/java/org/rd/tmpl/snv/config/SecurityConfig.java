package org.rd.tmpl.snv.config;

import java.util.Arrays;

import org.rd.tmpl.snv.dto.ERole;
import org.rd.tmpl.snv.util.AuthEntryPointJwt;
import org.rd.tmpl.snv.util.AuthTokenFilter;
import org.rd.tmpl.snv.util.JwtUtils;
import org.rd.tmpl.snv.util.UserDetailsServiceImpl;
import org.rd.tmpl.snv.util.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,  // The securedEnabled property determines if the @Secured annotation should be enabled.
    jsr250Enabled = true,   // The jsr250Enabled property allows us to use the @RoleAllowed annotation.
    prePostEnabled = true)  // The prePostEnabled property enables Spring Security pre/post annotations.
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        "/swagger-ui.html",
        "/swagger*/**",
        "/v3/api-docs/**",
        "/jwt/**" 
    };

    public SecurityConfig() {
        super();
    }

    @Value("${org.rd.tmpl.snv.secret}")
    private String secret;

    @Value("${org.rd.tmpl.snv.expiration}")
    private int expiration;

    @Value("${org.rd.tmpl.snv.authorities}")
    private String authorities;

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(secret, expiration, authorities);
    }

    @Bean
    public UserUtils userUtils() {
        UserUtils userUtils = new UserUtils();
        PasswordEncoder passwordEncoder = passwordEncoder();

        userUtils.add("jwt_root", passwordEncoder.encode("jwt_root"), 
                       Arrays.asList(ERole.ROLE_SELECT, ERole.ROLE_INSERT, ERole.ROLE_UPDATE, ERole.ROLE_DELETE));

        userUtils.add("jwt_support", passwordEncoder.encode("jwt_support"), 
                       Arrays.asList(ERole.ROLE_SELECT, ERole.ROLE_UPDATE));

        userUtils.add("jwt_guest", passwordEncoder.encode("jwt_guest"), 
                      Arrays.asList(ERole.ROLE_SELECT));

        return userUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
        .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(new AuthEntryPointJwt())
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), 
                             UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
 }