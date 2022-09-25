package com.springboot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.springboot.security.config.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .authorizeHttpRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                //.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                //.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                //.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                //.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(COURSE_WRITE.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/courses",true)
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remeber-me")
                .logoutSuccessUrl("/login");


    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails raviUser = User.builder()
                .username("ravi")
                .password(passwordEncoder.encode("password"))
           //     .roles(EMPLOYEE.name())//ROLE_EMPLOYEE
                .authorities(EMPLOYEE.getGrantedAuthority())
                .build();


        UserDetails rajUser = User.builder()
                .username("raj")
                .password(passwordEncoder.encode("password123"))
           //     .roles(ADMIN.name())//ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        UserDetails ramUser = User.builder()
                .username("raj")
                .password(passwordEncoder.encode("password123"))
            //    .roles(ADMINTRAINEE.name())//ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(raviUser,rajUser,ramUser);

    }
}
