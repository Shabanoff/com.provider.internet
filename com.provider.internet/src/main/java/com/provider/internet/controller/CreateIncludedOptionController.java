package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.model.entity.IncludedOption;
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

import java.util.ResourceBundle;

import static com.provider.internet.controller.util.constants.Attributes.*;

@Controller
@RequestMapping("/site/manager/create_option")
@RequiredArgsConstructor
@Slf4j
public class CreateIncludedOptionController {
    private final IncludedOptionService includedOptionService;
    private final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);
    @GetMapping
    public String viewCreatingPage() {
        return "createOption";
    }

    @PostMapping
    public String createIncludedOption(HttpServletRequest request,
                                       @RequestParam(DEFINITION) String definition) {
        IncludedOption includedOption = new IncludedOption();
        includedOption.setDefinition(definition);
        includedOptionService.createIncludedOption(includedOption);
        return REDIRECTED + bundle.getString("manager.includedOption.path");
    }
}
