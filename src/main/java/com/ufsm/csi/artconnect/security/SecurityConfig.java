package com.ufsm.csi.artconnect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private LoginSuccess customSuccessLogin;
     
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(getDaoAuthProvider())
            .build();
    }

    //define a criptografia usada e a classe que extende de UserDetailsService que gerencia o login
    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
    }

    //define criptografia na hora de verificar a senha no login
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
        
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf
                .disable())
                .addFilterAfter(new AcessoPaginaLoginRegister(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((request) -> {
                    request.requestMatchers("/login/**").permitAll()
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/resources/**","/css/**").permitAll()
                            .anyRequest().authenticated();
                })
                //.and()
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        //.loginProcessingUrl("/perform_login") //caso queira uma lógica propria de login
                        //.defaultSuccessUrl("/user", true)
                        //.failureUrl("/login?error=true")
                        .failureHandler(new LoginErro())//lógica propria se o login falhar
                        .failureUrl("/login/login-error")
                        .successHandler(customSuccessLogin))
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new Logout())
                        .logoutSuccessUrl("/login"))
                .headers(headers -> headers
                        .frameOptions()
                        .sameOrigin());//renderizar iframes mesma origin

        return http.build();
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/static/**");
    }
}
