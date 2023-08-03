package com.html0731.demo.config;

import java.security.Signature;
import java.util.Date;
import java.util.List;

import com.html0731.demo.MyApplication;
import com.html0731.demo.service.CustomOauth2Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.html0731.demo.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration // spring 객체 설정하는 클래스 입니다.
@EnableWebSecurity // spring security 재정의 하는 클래스입니다.
@RequiredArgsConstructor // @autowrid 안쓰고 private final 설정하는 거 객체 주입합니다.
public class MySecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOauth2Service oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 보안 토큰 넘어와야 되는데 그거 안하겠다.
                .csrf(csrf -> csrf.disable())
                // 크롬 브라우저 정책상 다른 요청을 허용하지 않는데 그거 안하겠다.
                .cors(cors -> cors.configurationSource(req -> {
                    var cosconfig = new CorsConfiguration();
                    cosconfig.setAllowedOrigins(List.of("*"));
                    cosconfig.setAllowedHeaders(List.of("*"));
                    cosconfig.setAllowedMethods(List.of("*"));
                    return cosconfig;
                }))
                .authorizeHttpRequests(
                        (authorizeRequest) ->
                                authorizeRequest
                                        // 회원가입 로그인 허용...
                                        .requestMatchers("/auth/**").permitAll()
                                        // user 로 요청하는 것은 허용
                                        .requestMatchers("/user/**").permitAll()
                                        // 다른것들은 인증이 필요하다...
                                        // 인증이 필요하기 때문에 구글 로그인으로
                                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2Login) -> oauth2Login
                                .userInfoEndpoint((userInfo)-> userInfo
                                .userService(oAuth2UserService)
                            )
                            .successHandler((request, response, authentication) -> {
                                //응답 하는게 문자열
                                response.setContentType("texet/html;charset=UTF-8");
                                Claims claims = Jwts.claims();
                                claims.put("username","kcs");

                                String mytoken = Jwts.builder()
                                        // username도 추가해서 암호화
                                        .setClaims(claims)
                                        // 현재 시간으로 발행
                                        .setIssuedAt(new Date(System.currentTimeMillis()))
                                        // 5분 뒤에 사용할 수 없음!
                                        .setExpiration(new Date(System.currentTimeMillis() + 1000*60*51))
                                        // KEY 값으로 암호화
                                        .signWith(SignatureAlgorithm.HS512, MyApplication.KEY)
                                        .compact();

                                var writer = response.getWriter();
                                        writer.println(mytoken);
                                    }
                            )
                )
                // session 방식 안하고 JWT 할 계획임
                .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Username 체크하기 전에 jwtAuthFilter 객체 들어가야함.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}