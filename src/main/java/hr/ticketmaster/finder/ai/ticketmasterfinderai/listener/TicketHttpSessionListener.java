package hr.ticketmaster.finder.ai.ticketmasterfinderai.listener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class TicketHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(final HttpSessionEvent event) {

        try {
            HttpServletRequest request =
                    ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

            String ipAddress =  request.getRemoteAddr();

            System.out.println("Session created! IP address = " + ipAddress);
        }
        catch (NullPointerException e) {
            System.out.println("Session not created! " + e.getMessage());
        }
    }
    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        System.out.println("Session destroyed!");
    }
}
