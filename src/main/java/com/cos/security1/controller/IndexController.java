package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다.
public class IndexController {

    @GetMapping("/")
    public String index() {
        //머스테치 기본폴더 src/main/resources
        //view 리졸버 설정: template (prefix), .mustache(suffix) 생략가능함요  - 의존성만 등록하면 그만이야
        return "index";
    }
    @GetMapping("/user")
    public String user(){
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
    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }
    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입 완료됨";
    }


}
