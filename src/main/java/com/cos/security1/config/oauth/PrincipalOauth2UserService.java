package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.auth.provider.FacebookUserInfo;
import com.cos.security1.config.auth.provider.GooleUserInfo;
import com.cos.security1.config.auth.provider.NaverUserInfo;
import com.cos.security1.config.auth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        //registrationId로 어떤 OAuth로 로그인했는지 확인가능,
        System.out.println("getClientRegistration : "+userRequest.getClientRegistration());
        System.out.println("getAccessToken : "+userRequest.getAccessToken());


        OAuth2User oauth2User=super.loadUser(userRequest);
        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        // userRequest정보 -> loaderUser함수 -> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes : "+oauth2User.getAttributes());

        //강제로 회원가입시키기
        OAuth2UserInfo  oAuth2UserInfo =null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){

            oAuth2UserInfo= new GooleUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo= new FacebookUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo= new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        }
        else{
            System.out.println("지원 안함 //config/oauth/PrincipalOauth2UserService 예외처리");
        }
        String provider = oAuth2UserInfo.getProvider();
        String providerId= oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String password = UUID.randomUUID().toString();
        String email= oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);



        if(userEntity == null){
             userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity,oauth2User.getAttributes());
    }
}
