package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.mapper.IncludedPackageMapper;
import com.provider.internet.service.IncludedPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.provider.internet.controller.util.constants.Attributes.INCLUDED_PACKAGE_ID;
import static com.provider.internet.controller.util.constants.Attributes.USER;
import static com.provider.internet.controller.util.constants.Views.ACCOUNT_VIEW;

@Controller
@RequestMapping("/site/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final IncludedPackageService includedPackageService;
    private final IncludedPackageMapper includedPackageMapper;

    @GetMapping
    public String viewReplenishPage(HttpServletRequest request, @SessionAttribute(USER) UserDto userDto) {
        List<IncludedPackage> includedPackages =
                includedPackageService.findByUser(userDto.getId());
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackageMapper.includedPackagesToIncludedPackagesDtoList(includedPackages));

        return ACCOUNT_VIEW;
    }

    @PostMapping
    public String disablePackage(HttpServletRequest request, @SessionAttribute(USER) UserDto userDto, @RequestParam(INCLUDED_PACKAGE_ID) long includedOptionId) {
        List<IncludedPackage> includedPackages =
                includedPackageService.findByUser(userDto.getId());
        includedPackageService.deleteIncludedPackage(includedOptionId);
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackageMapper.includedPackagesToIncludedPackagesDtoList(includedPackages));

        return ACCOUNT_VIEW;
    }

}
