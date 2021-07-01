package com.provider.internet.controller;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.mapper.IncludedPackageMapper;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.IncludedPackageService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.provider.internet.controller.util.constants.Attributes.INCLUDED_PACKAGE_ID;
import static com.provider.internet.controller.util.constants.Views.ACCOUNT_VIEW;

@Controller
@RequestMapping("/site/user/account")
@RequiredArgsConstructor
public class AccountController {
    private final IncludedPackageService includedPackageService;
    private final IncludedPackageMapper includedPackageMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String viewReplenishPage(HttpServletRequest request) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserById(sessionUser.getId());
        user.ifPresent(value -> request.setAttribute("user", userMapper.userToUserDto(value)));
        List<IncludedPackage> includedPackages =
                includedPackageService.findByUser(sessionUser.getId());
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackageMapper.includedPackagesToIncludedPackagesDtoList(includedPackages));

        return ACCOUNT_VIEW;
    }

    @PostMapping
    public String disablePackage(HttpServletRequest request, @RequestParam(INCLUDED_PACKAGE_ID) long includedOptionId) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<IncludedPackage> includedPackages =
                includedPackageService.findByUser(sessionUser.getId());
        includedPackageService.deleteIncludedPackage(includedOptionId);

        Optional<User> user = userService.findUserById(sessionUser.getId());
        user.ifPresent(value -> request.setAttribute("user", userMapper.userToUserDto(value)));
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackageMapper.includedPackagesToIncludedPackagesDtoList(includedPackages));

        return ACCOUNT_VIEW;
    }

}
