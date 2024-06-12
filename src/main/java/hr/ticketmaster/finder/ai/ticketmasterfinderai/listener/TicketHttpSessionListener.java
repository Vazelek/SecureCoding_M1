package hr.ticketmaster.finder.ai.ticketmasterfinderai.listener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TicketHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(final HttpSessionEvent event) {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String ipAddress =  request.getRemoteAddr();

        System.out.println("Session created! IP address = " + ipAddress);
    }
    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        System.out.println("Session destroyed!");
    }
}
