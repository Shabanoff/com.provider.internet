package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.REDIRECTED;
import static com.provider.internet.controller.util.constants.Attributes.USER_ID;
import static com.provider.internet.controller.util.constants.Views.USERS_VIEW;

@Controller
@RequestMapping("/site/manager/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    @GetMapping()
    public String viewUsers(HttpServletRequest request, Pageable pageable) {
        Page<UserDto> userDtoPage = userService.findAllUsers(PageRequest.of(pageable.getPageNumber(), 5)).map(userMapper::userToUserDto);
        request.setAttribute(Attributes.PAGES, userDtoPage.getTotalPages());
        request.setAttribute(Attributes.USERS, userDtoPage.getContent());
        request.setAttribute(Attributes.CURRENT_PAGE, pageable.getPageNumber());
        return USERS_VIEW;
    }

    @PostMapping
    public String changeUserStatus(HttpServletRequest request, @RequestParam(USER_ID) Long userId) {
        Optional<User> currentUserOpt = userService.findUserById(userId);
        if(currentUserOpt.isPresent()){
            User currentUser = currentUserOpt.get();
            if(Status.ACTIVE.equals(currentUser.getStatus())){
                userService.updateUserStatus(currentUser, Status.BLOCK);
            } else {
                userService.updateUserStatus(currentUser, Status.ACTIVE);
            }
        }
        request.setAttribute(Attributes.USERS, userMapper.usersToUsersDtoList(userService.findAll()));
        return "redirect:" + bundle.
                getString("users.path");
    }

}

