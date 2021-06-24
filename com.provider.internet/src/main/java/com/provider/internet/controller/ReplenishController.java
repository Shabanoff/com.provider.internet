package com.provider.internet.controller;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.validator.AmountValidator;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.mapper.IncludedPackageMapper;
import com.provider.internet.model.mapper.UserMapper;
import com.provider.internet.service.IncludedPackageService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.provider.internet.controller.util.constants.Attributes.AMOUNT;
import static com.provider.internet.controller.util.constants.Attributes.USER;
import static com.provider.internet.controller.util.constants.Views.ACCOUNT_VIEW;
import static com.provider.internet.controller.util.constants.Views.REPLENISH_VIEW;

@Controller
@RequestMapping("/site/user/replenish")
@RequiredArgsConstructor
@Slf4j
public class ReplenishController {
    private final UserService userService;
    private final IncludedPackageService includedPackageService;
    private final IncludedPackageMapper includedPackageMapper;
    private final UserMapper userMapper;

    @GetMapping
    public String viewReplenishPage() {
        return REPLENISH_VIEW;
    }

    @PostMapping
    public String changeUserBalance(HttpSession session, HttpServletRequest request,
                                    @RequestParam(AMOUNT) BigDecimal amount) {
        List<String> errors = validateDataFromRequest(request);
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (errors.isEmpty()) {
            User user = userService.increaseUserBalance(sessionUser.getId(), amount);
            List<IncludedPackage> includedPackages = includedPackageService.findByUser(sessionUser.getId());
            request.setAttribute(Attributes.INCLUDED_PACKAGES,
                    includedPackageMapper.includedPackagesToIncludedPackagesDtoList(includedPackages));
            session.setAttribute(Attributes.USER, userMapper.userToUserDto(user));
            return ACCOUNT_VIEW;
        }
        log.info("Amount HAS ERRORS!");
        request.setAttribute(Attributes.ERRORS, errors);

        return REPLENISH_VIEW;
    }

    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new AmountValidator(),
                request.getParameter(Attributes.AMOUNT), errors);
        return errors;
    }
}
