package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.provider.internet.controller.util.constants.Attributes.USER_ID;
import static com.provider.internet.controller.util.constants.Views.USERS_VIEW;

@Controller
@RequestMapping("/site/manager/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String viewUsers(HttpServletRequest request) {

        request.setAttribute(Attributes.USERS, userMapper.usersToUsersDtoList(userService.findAll()));
        return USERS_VIEW;
    }

    @PostMapping
    public String changeUserStatus(HttpServletRequest request, @RequestParam(USER_ID) Long userId) {
        Optional<User> currentUser = userService.findUserById(userId);
        currentUser.ifPresent(userService::updateUserStatus);
        request.setAttribute(Attributes.USERS, userMapper.usersToUsersDtoList(userService.findAll()));
        return USERS_VIEW;
    }

}

