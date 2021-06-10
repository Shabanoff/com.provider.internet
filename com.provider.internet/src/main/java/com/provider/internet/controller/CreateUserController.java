package com.provider.internet.controller;

import com.provider.internet.controller.util.PasswordStorage;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Role;
import com.provider.internet.model.enums.Status;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.IncludedOptionService;
import com.provider.internet.service.ServiceService;
import com.provider.internet.service.TariffService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.provider.internet.controller.util.constants.Attributes.*;

@Controller
@RequestMapping("/site/manager/create_user")
@RequiredArgsConstructor
@Slf4j
public class CreateUserController {
        private final UserService userService;
        private final UserMapper userMapper;

        @GetMapping
        public String viewCreatingPage() {
            return "createUser";
        }

        @PostMapping
        public String createUser(HttpServletRequest request,
                                 @RequestParam(LOGIN) String login,
                                 @RequestParam(PASSWORD) String password) {
            List<User> users = userService.findAll();
            List<String> errors = new ArrayList<>();
            for (User user: users) {
                if (user.getLogin().equals(login)){
                    errors.add("This login has already booked");
                }
            }
            if (errors.isEmpty()) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(PasswordStorage.getSecurePassword(password));
                user.setStatus(Status.ACTIVE);
                user.setBalance(BigDecimal.ZERO);
                user.setRole(Role.USER);
                userService.createUser(user);

               request.setAttribute(Attributes.USERS, userMapper.usersToUsersDtoList(users));
               return "users";
            }
            log.info("This login has already booked");
            request.setAttribute(Attributes.ERRORS, errors);
            return "createUser";
        }
}

