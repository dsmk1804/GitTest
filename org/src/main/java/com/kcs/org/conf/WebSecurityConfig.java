package com.kcs.org.conf;


import com.kcs.org.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    MemberService memberService;

    // 로그인 처리
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
        // 로그인 설정
        http
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/Member/**", "/images/**").permitAll()
                        .requestMatchers( "/account/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/FreeBoard/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        (form)->
                                form.loginPage("/account/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/account/login?error")
                                .permitAll())

                .logout((logout)->logout.permitAll());

        return http.build();
    }



}
