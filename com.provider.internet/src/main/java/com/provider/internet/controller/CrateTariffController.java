package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.model.entity.IncludedOption;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.mapper.OptionMapper;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.service.IncludedOptionService;
import com.provider.internet.service.ServiceService;
import com.provider.internet.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

import static com.provider.internet.controller.util.constants.Attributes.*;
import static com.provider.internet.controller.util.constants.Views.CREATE_TARIFF_VIEW;

@Controller
@RequestMapping("/site/manager/create_tariff")
@RequiredArgsConstructor
@Slf4j
public class CrateTariffController {
    private final TariffService tariffService;
    private final IncludedOptionService includedOptionService;
    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;
    private final OptionMapper includedOptionMapper;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    @GetMapping
    public String viewCreatingPage(HttpServletRequest request) {
        request.setAttribute(SERVICES,
                serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        request.setAttribute(Attributes.OPTIONS, includedOptionMapper.
                includedOptionsToIncludedOptionsDtoList(includedOptionService.findAllIncludedOption()));
        return CREATE_TARIFF_VIEW;
    }

    @PostMapping
    public String createTariff(HttpServletRequest request,
                               @RequestParam(TARIFF_NAME) String tariffName,
                               @RequestParam(COST) BigDecimal cost,
                               @RequestParam(SERVICE_ID) long serviceId,
                               @RequestParam(OPTION_ID) String[] optionsId) {
        Tariff tariff = new Tariff();

        Set<IncludedOption> includedOptions = new HashSet<>();
        for (String optionId : optionsId) {
            Optional<IncludedOption> includedOption = includedOptionService.findIncludedOptionById(Long.parseLong(optionId));
            includedOption.ifPresent(includedOptions::add);
        }

        tariff.setIncludedOptions(includedOptions);
        tariff.setCost(cost);
        if (serviceService.findServiceById(serviceId).isPresent())
        tariff.setService(serviceService.findServiceById(serviceId).get());
        tariff.setTariffName(tariffName);
        tariffService.createTariff(tariff);
        return REDIRECTED + bundle.getString("service.path");
    }
}
