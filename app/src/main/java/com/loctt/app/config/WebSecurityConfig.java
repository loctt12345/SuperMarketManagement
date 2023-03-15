package com.loctt.app.config;

import com.loctt.app.service.impl.CustomOAuth2UserService;
import com.loctt.app.service.impl.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private OAuth2SuccessLoginHandler oAuth2SuccessLoginHandler;


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticatorProvider = new DaoAuthenticationProvider();
        authenticatorProvider.setPasswordEncoder(passwordEncoder());
        authenticatorProvider.setUserDetailsService(userDetailsService());
        return authenticatorProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessLoginHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/authorize", true);

        http.logout();

        http.authenticationProvider(authProvider());

        http.authorizeRequests()
                .antMatchers("/login", "/", "/js/**", "/css/**",
                        "/product-detail","/api/products/**", 
                        "/showBill", "/register", "/addUser").permitAll()
                .antMatchers("/api/cart/**", "/showCart",
                        "/showPaying", "/paying/**").hasRole("USER")
                .antMatchers("/admin-page", "/admin/**", "/crawl", "/dashboard","/admin-employee-page").hasRole("ADMIN")
                .antMatchers("/shipStaff", "/shipper_summary_order",
                        "api/order/**").hasRole("DELIVERY_MAN")
                .antMatchers("/repoStaff", "api/order/**").hasRole("STORAGE_MAN")
                .anyRequest()
                .authenticated();
        
        http.exceptionHandling().accessDeniedPage("/accessDenied");
        
        http.csrf().disable();
        return http.build();
    }
}
