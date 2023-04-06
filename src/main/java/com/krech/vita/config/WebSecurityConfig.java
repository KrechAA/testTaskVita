package com.krech.vita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.krech.vita.repository.RoleRepositoryImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_API = "/auth/**";
    private static final String ADMIN_API = "/admin/**";
    private static final String OPERATOR_API = "/operator/**";
    private static final String USER_API = "/user/**";


    private final RoleRepositoryImpl roleRepository;
    private final JwtFilter jwtFilter;

    @Autowired
    public WebSecurityConfig(RoleRepositoryImpl roleRepository, JwtFilter jwtFilter) {
        this.roleRepository = roleRepository;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_API).permitAll()
                .antMatchers(ADMIN_API).hasAnyRole(roleRepository.getRoleById(1).getName())
                .antMatchers(OPERATOR_API).hasAnyRole(roleRepository.getRoleById(2).getName())
                .antMatchers(USER_API).hasAnyRole(roleRepository.getRoleById(3).getName())
                .and()
                .csrf().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
