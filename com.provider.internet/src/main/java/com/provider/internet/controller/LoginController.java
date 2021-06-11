package com.provider.internet.controller;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.controller.util.validator.LoginValidator;
import com.provider.internet.controller.util.validator.PasswordValidator;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Role;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.provider.internet.controller.util.constants.Attributes.*;
import static com.provider.internet.controller.util.constants.Views.LOGIN_VIEW;

@Controller
@RequestMapping("/site/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String viewLogin() {
        return LOGIN_VIEW;
    }

    @SneakyThrows
    @PostMapping
    public String postLogin(HttpServletRequest request,
                            @RequestParam(LOGIN) String login,
                            @RequestParam(PASSWORD) String password) {
        if (Util.isAlreadyLoggedIn(request.getSession())) {
            return "redirect:" + bundle.
                    getString("account.path");
        }

        UserDto userDto = getDataFromRequest(request, login, password);

        List<String> errors = validateData(userDto);
        errors.addAll(
                validateUniquenessActiveUser(request.getSession(), userDto));

        if (errors.isEmpty()) {
            log.info("LOGGIN WITHOUT ERRORS!");
            UserDto user = loadUserFromDatabase(userDto.getLogin());
            addUserToContext(request.getSession(), user);
            addUserToSession(request.getSession(), user);
            if (user.getRole().equals(Role.MANAGER)) {
                return "redirect:" + bundle.
                        getString("users.path");
            }
            return "redirect:" + bundle.
                    getString("account.path");
        }
        log.info("LOGGIN HAS ERRORS!");
        addInvalidDataToRequest(request, userDto, errors);

        return LOGIN_VIEW;
    }

    private UserDto getDataFromRequest(HttpServletRequest request,
                                       String login,
                                       String password) {
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(password);

        return userDto;
    }

    private List<String> validateData(UserDto user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);

        /* Check if entered password matches with user password only in case,
            when email and password is valid
        */
        if (errors.isEmpty() && !userService.
                isCredentialsValid(user.getLogin(), user.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }

        return errors;
    }

    public List<String> validateUniquenessActiveUser(HttpSession session, UserDto user) {
        List<String> errors = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Map<String, UserDto> activeUserList = (Map<String, UserDto>) session.getServletContext().
                getAttribute(Attributes.USER_LIST);
        if (activeUserList.get(user.getLogin()) != null)
            errors.add(ACTIVE_ACCOUNT_IS_EXIST);
        return errors;
    }

    private UserDto loadUserFromDatabase(String login) {

        Optional<User> userOptional = userService.findByLogin(login);
        if (userOptional.isPresent()) {
            return userMapper.userToUserDto(userOptional.get());
        }
        throw new IllegalStateException();
    }

    private void addUserToSession(HttpSession session, UserDto user) {
        session.setAttribute(Attributes.USER, user);
    }

    private void addUserToContext(HttpSession session, UserDto user) {
        @SuppressWarnings("unchecked")
        Map<String, UserDto> activeUserList = (Map<String, UserDto>) session.getServletContext().
                getAttribute(Attributes.USER_LIST);
        activeUserList.put(user.getLogin(), user);
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         UserDto user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER, user);
        request.setAttribute(Attributes.ERRORS, errors);
    }
}
