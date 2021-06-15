package com.provider.internet.controller;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.controller.util.validator.LoginValidator;
import com.provider.internet.model.entity.Role;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.LOGIN;
import static com.provider.internet.controller.util.constants.Attributes.PASSWORD;
import static com.provider.internet.controller.util.constants.Views.CREATE_USER_VIEW;

@Controller
@RequestMapping("/site/manager/create_user")
@RequiredArgsConstructor
@Slf4j
public class CreateUserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    @GetMapping
    public String viewCreatingPage() {
        return CREATE_USER_VIEW;
    }

    @PostMapping
    public String createUser(HttpServletRequest request,
                             @RequestParam(LOGIN) String login,
                             @RequestParam(PASSWORD) String password) {
        List<User> users = userService.findAll();
        List<String> errors = validateDataFromRequest(request);
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                errors.add("login.booked");
                log.info("login.booked");
            }
        }
        if (errors.isEmpty()) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setStatus(Status.ACTIVE);
            user.setBalance(BigDecimal.ZERO);
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            userService.createUser(user);

            return "redirect:" + bundle.
                    getString("users.path");
        }
        request.setAttribute(Attributes.ERRORS, errors);
        return CREATE_USER_VIEW;
    }

    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new LoginValidator(),
                request.getParameter(LOGIN), errors);
        return errors;
    }
}

