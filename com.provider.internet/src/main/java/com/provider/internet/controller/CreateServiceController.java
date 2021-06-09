package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Views;
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

import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.*;

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
        return "createService";
    }

    @PostMapping
    public String createService(HttpServletRequest request, @RequestParam(SERVICE_NAME) String serviceName) {
        Service service = new Service();
        service.setServiceName(serviceName);
        serviceService.createService(service);
        return "redirect:" + bundle.
                getString("service.path");
    }
}
