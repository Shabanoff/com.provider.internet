package com.provider.internet.controller.i18n.filter;


import com.provider.internet.controller.i18n.SupportedLocale;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getParameter(LANG) != null) {
            replaceUserLocale(req, resp);
        }

        if (req.getSession().getAttribute(LOCALE) == null && req.getCookies().length!=0) {
            setUserLocale(req, resp);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void replaceUserLocale(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String langParameter = request.getParameter(LANG);
        Cookie localeCookie_lang = new Cookie( "locale", SupportedLocale.getLocaleOrDefault(langParameter).getLanguage() );
        response.addCookie( localeCookie_lang );
        Locale locale = SupportedLocale.getLocaleOrDefault(langParameter);
        session.setAttribute(LOCALE, locale);
    }

    private void setUserLocale(HttpServletRequest request, HttpServletResponse response) {
        Cookie localeCookie_lang = new Cookie( "locale", SupportedLocale.getDefault().getLanguage() );
        response.addCookie( localeCookie_lang );
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }
}
