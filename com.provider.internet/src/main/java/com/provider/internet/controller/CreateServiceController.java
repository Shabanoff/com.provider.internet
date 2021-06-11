package com.provider.internet.controller;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.controller.util.validator.AmountValidator;
import com.provider.internet.controller.util.validator.ServiceIdValidator;
import com.provider.internet.controller.util.validator.ServiceNameValidator;
import com.provider.internet.controller.util.validator.TariffNameValidator;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.*;
import static com.provider.internet.controller.util.constants.Views.CREATE_SERVICE_VIEW;
import static com.provider.internet.controller.util.constants.Views.CREATE_TARIFF_VIEW;

@Controller
@RequestMapping("/site/manager/create_service")
@RequiredArgsConstructor
@Slf4j
public class CreateServiceController {
    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    @GetMapping
    public String viewCreatingPage() {
        return CREATE_SERVICE_VIEW;
    }

    @PostMapping
    public String createService(HttpServletRequest request, @RequestParam(SERVICE_NAME) String serviceName) {
        List<String> errors = validateDataFromRequest(request);
        if (!errors.isEmpty()){
                request.setAttribute(Attributes.ERRORS, errors);
                return CREATE_SERVICE_VIEW;

        }
        Service service = new Service();
        service.setServiceName(serviceName);
        serviceService.createService(service);
        return "redirect:" + bundle.
                getString("service.path");
    }
    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new ServiceNameValidator(),
                request.getParameter(SERVICE_NAME), errors);
        return errors;
    }
}
