package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.service.ServiceService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.provider.internet.controller.util.constants.Attributes.*;

@Controller
@RequestMapping("/site/service")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {
    private final ServiceService serviceService;
    private final UserService userService;
    private final ServiceMapper serviceMapper;

    @GetMapping
    public String viewService(HttpServletRequest request) {
        request.setAttribute(Attributes.SERVICES,
                serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return "service";
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
        return "service";
    }

    @PostMapping("/update")
    public String plugTariff(HttpServletRequest request,
                             @SessionAttribute(USER) UserDto userDto,
                             @RequestParam(TARIFF_ID) Long tariffId) {

        List<String> errors = userService.updateUserTariff(userDto.getId(), tariffId);
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        if (!errors.isEmpty()) {
            request.setAttribute(Attributes.ERRORS, errors);
            log.info("Adding tariff HAS ERRORS!");
        }
        return "service";
    }

}
