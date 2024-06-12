package hr.ticketmaster.finder.ai.ticketmasterfinderai.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class TransactionFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        log.trace("Starting a transaction for req : {}",
                req.getRequestURI());

        chain.doFilter(request, response);
        log.trace("Committing a transaction for req : {}",
                req.getRequestURI());
    }
}


