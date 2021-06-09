package com.provider.internet.controller.listener;

import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.Objects;

@WebListener
@Slf4j
public class UniquenessCheckSessionListener implements HttpSessionListener {

    private static int totalActiveSessions;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        totalActiveSessions++;
        sessionEvent.getSession().setMaxInactiveInterval(5*60);
        log.debug("Created new session: " + sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        totalActiveSessions--;
        ServletContext context = sessionEvent.getSession().getServletContext();
        UserDto user = (UserDto) sessionEvent.getSession().getAttribute(Attributes.USER);
        @SuppressWarnings("unchecked")
        Map<String,UserDto> activeUserList = (Map<String,UserDto>)
                context.getAttribute(Attributes.USER_LIST);
        log.debug("Destroyed session: " + sessionEvent.getSession().getId()+
                (Objects.nonNull(user)? "; User: "+user.getLogin()+";":";"));
        activeUserList.remove(user.getLogin());
    }
}

