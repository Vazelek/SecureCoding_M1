package hr.ticketmaster.finder.ai.ticketmasterfinderai.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String method = httpServletRequest.getMethod();

        for(String paramName : httpServletRequest.getParameterMap().keySet()) {
            log.trace("Parameter name: " + paramName + " " + httpServletRequest.getParameter(paramName));
        }

        log.trace("Method is: " + method);

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        for(String headerName : httpServletResponse.getHeaderNames()) {
            log.trace("Header parameter: " + headerName + " " + httpServletResponse.getHeader(headerName));
        }

    }
}
