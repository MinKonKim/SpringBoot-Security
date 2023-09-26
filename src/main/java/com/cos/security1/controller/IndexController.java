package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View를 리턴하겠다.
public class IndexController {

    @GetMapping("/")
    public String index() {
        //머스테치 기본폴더 src/main/resources
        //view 리졸버 설정: template (prefix), .mustache(suffix) 생략가능함요  - 의존성만 등록하면 그만이야
        return "index";
    }
}
