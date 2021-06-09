package com.provider.internet.controller;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.validator.AmountValidator;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.service.ServiceService;
import com.provider.internet.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.provider.internet.controller.util.constants.Attributes.*;

@Controller
@RequestMapping("/site/tariff")
@RequiredArgsConstructor
@Slf4j
public class TariffController {
    private final ServiceService serviceService;
    private final TariffService tariffService;
    private final ServiceMapper serviceMapper;

    @GetMapping
    public String viewService(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return "service";
    }

    @PostMapping("/update")
    public String changeTariffCost(HttpServletRequest request, @RequestParam(TARIFF_ID) Long tariffId) {
        List<String> errors = validateDataFromRequest(request);
        if (!errors.isEmpty()){
            request.setAttribute(Attributes.ERRORS, errors);
            log.info(errors.toString());
        }else{
        Optional<Tariff> tariff = tariffService.findTariffById(tariffId);
        tariff.ifPresent(value -> tariffService.changeCost(value, new BigDecimal(request.getParameter(Attributes.AMOUNT))));
        }
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return "service";
    }

    @PostMapping("/delete")
    public String deleteTariff(HttpServletRequest request, @RequestParam(TARIFF_ID) Long tariffId) {
        List<String> errors = tariffService.deleteTariff(tariffId);
        if (!errors.isEmpty()) {
            log.info("Tariff has already add to user");
            request.setAttribute(Attributes.ERRORS, errors);
        }
        request.setAttribute(Attributes.SERVICES, serviceMapper.serviceListToServiceDtoList(serviceService.findAllService()));
        return "service";
    }
    @PostMapping
    public String sortTariff(HttpServletRequest request, @RequestParam(TYPE_SORT) String typeSort) {
        request.setAttribute(Attributes.SERVICES,
                serviceMapper.serviceListToServiceDtoList(
                        serviceService.sortByCostTariff(typeSort)));
        return "service";
    }
    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new AmountValidator(),
                request.getParameter(Attributes.AMOUNT), errors);
        return errors;
    }

}
