package com.provider.internet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.provider.internet.controller.util.constants.Views.HOME_VIEW;

@Controller
@RequestMapping("/site/logout")
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    @GetMapping
    public String viewService(HttpServletRequest request) {
        request.getSession().invalidate();
        return HOME_VIEW;
    }
}
