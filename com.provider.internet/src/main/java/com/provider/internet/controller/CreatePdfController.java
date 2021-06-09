package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.service.CreatePdfService;
import com.provider.internet.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.REDIRECTED;

@Controller
@RequestMapping("/site/downloadPdf")
@RequiredArgsConstructor
@Slf4j
public class CreatePdfController {
    private final CreatePdfService createPdfService;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);
    private final ServiceMapper serviceMapper;
    private final ServiceService serviceService;

    @GetMapping
    public String viewService(HttpServletRequest request) {
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return "service";
    }
    @SneakyThrows
    @PostMapping
    public String creatingPdfPage(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=response.pdf");
        try(OutputStream out = response.getOutputStream()) {
            createPdfService.createServiceInPdf(out);
        }
        return REDIRECTED + bundle.getString("service.path");
    }
}
