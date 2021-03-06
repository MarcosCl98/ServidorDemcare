package com.demcare.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/script/**","/", "/singup", "/login/**", "/data","/suspended").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/cuidador/**").hasAnyAuthority("ROLE_CUIDADOR")
                .antMatchers("/institucion/**").hasAnyAuthority("ROLE_INSTITUCION")
                .antMatchers("/jugador/**").hasAnyAuthority("ROLE_JUGADOR")
                .anyRequest().authenticated().and().formLogin()
                .loginPage("/login").defaultSuccessUrl("/home").and()
                .logout()
                .permitAll();

    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
