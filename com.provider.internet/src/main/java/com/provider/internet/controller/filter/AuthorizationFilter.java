package com.provider.internet.controller.filter;

import com.provider.internet.controller.util.Util;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.controller.util.constants.Views;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class AuthorizationFilter implements Filter {
    public final static int MANAGER_ROLE_ID = 1;
    public final static int USER_ROLE_ID = 2;
    private final static Logger logger = LogManager.getLogger(AuthorizationFilter.class);
    private final static String ACCESS_DENIED = "Access denied for page: ";
    private static final String SITE_PREFIX = "site.prefix";
    private static final String USER_PREFIX = "user.prefix";
    private static final String MANAGER_PREFIX = "manager.prefix";
    private static final String HOME_PATH = "home.path";
    private static final String LOGIN_PATH = "login.path";
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (!isUserLoggedIn(request)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    bundle.getString(LOGIN_PATH)
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        UserDto user = getUserFromSession(request.getSession());

        if (isUserRoleInvalidForRequestedPage(request, user)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    bundle.getString(HOME_PATH)
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(Attributes.USER) != null;
    }

    private UserDto getUserFromSession(HttpSession session) {
        return (UserDto) session.getAttribute(Attributes.USER);
    }

    private boolean isUserRoleInvalidForRequestedPage(HttpServletRequest request,
                                                      UserDto user) {
        return (isUserPage(request) && !user.getRole().equals(Role.USER)) ||
                (isManagerPage(request) && !user.getRole().equals(Role.MANAGER));
    }

    private boolean isUserPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(bundle.getString(SITE_PREFIX) +
                        bundle.getString(USER_PREFIX));
    }

    private boolean isManagerPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(bundle.getString(SITE_PREFIX) +
                        bundle.getString(MANAGER_PREFIX));
    }

    private void logInfoAboutAccessDenied(String uri) {
        logger.info(ACCESS_DENIED + uri);
    }
}

