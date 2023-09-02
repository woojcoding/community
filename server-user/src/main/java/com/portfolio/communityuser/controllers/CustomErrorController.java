package com.portfolio.communityuser.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Custom Error controller.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private final String ERROR_PATH = "/error";

    /**
     * 에러 발생 시 index.html을 반환
     * @return index.html
     */
    @GetMapping(ERROR_PATH)
    public String redirectRoot(){
        return "index.html";
    }
}
