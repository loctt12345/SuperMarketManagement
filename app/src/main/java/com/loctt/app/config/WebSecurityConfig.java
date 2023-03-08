package com.loctt.app.config;

import com.loctt.app.security.oauth.CustomOAuth2User;
import com.loctt.app.security.oauth.CustomOAuth2UserService;
import com.loctt.app.security.oauth.OAuth2LoginSucessHandler;
import com.loctt.app.service.impl.SecurityUserDetailsService;
import javax.activation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticatorProvider = new DaoAuthenticationProvider();
        authenticatorProvider.setPasswordEncoder(passwordEncoder());
        authenticatorProvider.setUserDetailsService(userDetailsService());
        return authenticatorProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
        http.authenticationProvider(authProvider());
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/js/**", "/css/**").permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/customer/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                    .loginPage("/login_form")
                    .permitAll()
                    .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                    .loginPage("/login_form")
                    .userInfoEndpoint().userService(oAuth2UserService)
                    .and()
                    .successHandler(oauth2LoginSucessHandler)
                .and()
                .logout().permitAll()
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository());

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource((javax.sql.DataSource) dataSource);
        return tokenRepository;
    }

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    
    @Autowired
    private OAuth2LoginSucessHandler oauth2LoginSucessHandler;
}
