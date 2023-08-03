package com.html0731.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@Slf4j
// log 파일에 설정하게 되면 콘솔에 출력하지 않고 파일에 출력 할 수 있다.
// 파일에 에러내용만 출력하게 되면 실제 서버 운영시
// 그 다음날에 파일을 열어서 에러 내용 확인 가능하다..
// 콘솔은 개발시에 사용할수 있고 파일은 실제 서버 운영시에 출력내용을 적용할수 있다.
public class CustomOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        log.info("oAuth2User ={}",oAuth2User);
        log.info("userRequest = {}",userRequest);
        log.info("userRequest.getAccessToken() = {}",userRequest.getAccessToken());
        log.info("userRequest.getClientRegistration() = {}",userRequest.getClientRegistration());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                null, "email");
    }

/*    // 세션
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return null;
    } */
}
