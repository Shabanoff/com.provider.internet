package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.ServiceService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.provider.internet.controller.util.constants.Attributes.*;
import static com.provider.internet.controller.util.constants.Views.SERVICE_VIEW;

@Controller
@RequestMapping("/site/service")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {
    private final ServiceService serviceService;
    private final UserService userService;
    private final ServiceMapper serviceMapper;
    private final UserMapper userMapper;

    @GetMapping
    public String viewService(HttpServletRequest request) {
        request.setAttribute(Attributes.SERVICES,
                serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return SERVICE_VIEW;
    }

    @PostMapping("/delete")
    public String deleteService(HttpServletRequest request,
                                @RequestParam(SERVICE_ID) Long serviceId) {
        List<String> errors = serviceService.deleteService(serviceId);
        if (!errors.isEmpty()) {
            log.info("Service has already add to user");
            request.setAttribute(Attributes.ERRORS, errors);
        }
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return SERVICE_VIEW;
    }

    @PostMapping("/update")
    public String plugTariff(HttpSession session, HttpServletRequest request,
                             @RequestParam(TARIFF_ID) Long tariffId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> errors = userService.updateUserTariff(user.getId(), tariffId);
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        if (!errors.isEmpty()) {
            request.setAttribute(Attributes.ERRORS, errors);
            log.info("Adding tariff HAS ERRORS!");
        }
        session.setAttribute(Attributes.USER, userMapper.userToUserDto(userService.findUserById(user.getId()).get()));
        return SERVICE_VIEW;
    }

}
