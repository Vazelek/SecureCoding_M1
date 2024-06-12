package hr.ticketmaster.finder.ai.ticketmasterfinderai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
public class TicketFilter extends TicketSearchForm {
    private Integer id;

    public TicketFilter(Integer id, String type, String eventName, String venue,
                        String description, String eventTimeFromString,
                        String eventTimeToString, BigDecimal fromPrice,
                        BigDecimal toPrice) {
        super(type, eventName, venue, description, eventTimeFromString, eventTimeToString,
                fromPrice, toPrice);
        this.id = id;
    }

    public TicketFilter(String type, String eventName, String venue,
                        String description, String eventTimeFromString,
                        String eventTimeToString, BigDecimal fromPrice,
                        BigDecimal toPrice) {
        super(type, eventName, venue, description, eventTimeFromString, eventTimeToString,
                fromPrice, toPrice);
    }
}
