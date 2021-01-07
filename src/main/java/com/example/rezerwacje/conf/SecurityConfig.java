package com.example.rezerwacje.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("user").password(encoder.encode("password")).roles("USER").and()
//                .withUser("admin").password(encoder.encode("password")).roles("USER", "ADMIN");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select nazwa, haslo, true " +
                        "from UZYTKOWNICY where nazwa=?")
                .authoritiesByUsernameQuery("select nazwa, rola from UZYTKOWNICY where nazwa=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                //.loginPage("/login")
                .and()
                .logout()//.logoutSuccessUrl("/")//.logoutUrl("/logout")
                .and()
                .rememberMe()//.tokenValiditySeconds(2419200)
                .and()
                .httpBasic()//.realmName("Spittr")
                .and()
                .authorizeRequests()
                //.antMatchers("/spitter/me","/spittles/mine").authenticated()
                .antMatchers(/*HttpMethod.POST, */"/{miasto}/{nazwa}/{id}").hasRole("KLIENT") //fixme powinno działać, a nie działa :<
                .anyRequest().permitAll().and()
                .requiresChannel()
                //.antMatchers("/spitter/form").requiresSecure()
                .antMatchers("/").requiresInsecure();
    }
}

