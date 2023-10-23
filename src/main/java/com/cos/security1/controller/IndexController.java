package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다.
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryPasswordEncoder;


//    @GetMapping("/test/login")
//    public @ResponseBody String testLogin(Authentication authentication,
//                           /*세션정보 접근*/@AuthenticationPrincipal PrincipalDetails userDetails){
//
//        System.out.println("/test/login =====================");
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("authentication :" +principalDetails.getUser());
//
//
//        System.out.println("userDetails : "+userDetails.getUser());
//
//        return "세션 정보 확인하기.";
//    }
//
//    @GetMapping("/test/oauth/login")
//    public @ResponseBody String testOAuthLogin(Authentication authentication,
//                                               @AuthenticationPrincipal OAuth2User oauth){
//
//        System.out.println("/test/oauth/login =====================");
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("getAttribute :" +oauth2User.getAttributes());
//        System.out.println("oauth2User : "+oauth.getAttributes());
//
//        return " OAuth 세션 정보 확인하기.";
//    }



    //localhost:8080/
    @GetMapping({"", "/"})
    public String index() {
        //머스테치 기본폴더 src/main/resources
        //view 리졸버 설정: template (prefix), .mustache(suffix) 생략가능함요  - 의존성만 등록하면 그만이야
        return "index";
    }
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails : "+principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        // 패스워드 암호화
        user.setPassword(bCryPasswordEncoder.encode(rawPassword));
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }

}
