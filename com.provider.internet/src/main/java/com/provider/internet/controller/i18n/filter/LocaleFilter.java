package com.provider.internet.controller.i18n.filter;


import com.provider.internet.controller.i18n.SupportedLocale;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@Component
public class LocaleFilter implements Filter {
    private static final String LANG = "lang";
    private static final String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if(req.getParameter(LANG) != null) {
            replaceUserLocale(req);
        }

        if (req.getSession().getAttribute(LOCALE) == null) {
            setUserLocale(req);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void replaceUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String langParameter = request.getParameter(LANG);
        Locale locale = SupportedLocale.getLocaleOrDefault(langParameter);
        session.setAttribute(LOCALE, locale);
    }

    private void setUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }
}
