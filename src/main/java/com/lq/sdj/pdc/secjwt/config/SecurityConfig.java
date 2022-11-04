package com.lq.sdj.pdc.secjwt.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lq.sdj.pdc.secjwt.dto.ERole;
import com.lq.sdj.pdc.secjwt.util.AuthEntryPointJwt;
import com.lq.sdj.pdc.secjwt.util.AuthTokenFilter;
import com.lq.sdj.pdc.secjwt.util.JwtUtils;
import com.lq.sdj.pdc.secjwt.util.UserDetailsServiceImpl;
import com.lq.sdj.pdc.secjwt.util.UserUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,  // The securedEnabled property determines if the @Secured annotation should be enabled.
    jsr250Enabled = true,   // The jsr250Enabled property allows us to use the @RoleAllowed annotation.
    prePostEnabled = true)  // The prePostEnabled property enables Spring Security pre/post annotations.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    };

    public SecurityConfig()
    {
        super();
    }

    @Value("${com.lq.sdj.pdc.secjwt.secret}")
    private String secret;

    @Value("${com.lq.sdj.pdc.secjwt.expiration}")
    private int expiration;

    @Value("${com.lq.sdj.pdc.secjwt.authorities}")
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
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(new AuthEntryPointJwt())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests().antMatchers("/jwt/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}