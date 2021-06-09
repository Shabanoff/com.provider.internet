package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.mapper.OptionMapper;
import com.provider.internet.service.IncludedOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.provider.internet.controller.util.constants.Attributes.OPTION_ID;

@Controller
@RequestMapping("/site/manager/included_option")
@RequiredArgsConstructor
@Slf4j
public class IncludedOptionController {
    private final IncludedOptionService includedOptionService;
    private final OptionMapper includedOptionMapper;

    @GetMapping
    public String viewCreatingPage(HttpServletRequest request) {
        request.setAttribute(Attributes.OPTIONS, includedOptionService.findAllIncludedOption());
        return "includedOption";
    }

    @PostMapping
    public String deleteIncludedOption(HttpServletRequest request, @RequestParam(OPTION_ID) Long optionsId) {
        includedOptionService.deleteIncludedOption(optionsId);
        request.setAttribute(Attributes.OPTIONS, includedOptionMapper.
                includedOptionsToIncludedOptionsDtoList(includedOptionService.findAllIncludedOption()));
        return "includedOption";
    }

}
