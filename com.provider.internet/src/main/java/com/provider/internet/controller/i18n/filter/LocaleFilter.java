package com.provider.internet.controller.i18n.filter;


import com.provider.internet.controller.i18n.SupportedLocale;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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

        setUserLocale(req, resp);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void setUserLocale(HttpServletRequest request, HttpServletResponse response) {

        String langParameter = request.getParameter(LANG);
        Locale locale;
        if (Strings.isNotBlank(langParameter)) {
            locale = SupportedLocale.getLocaleOrDefault(langParameter);
            Cookie localeCookie_lang = new Cookie("locale", locale.getLanguage());
            response.addCookie(localeCookie_lang);
        } else {
            locale = SupportedLocale.getLocaleOrDefault(
                    Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(LOCALE)).
                            map(Cookie::getValue).findFirst().orElse(SupportedLocale.getDefault().getLanguage()));
        }
        request.setAttribute(LOCALE, locale);
    }


}
