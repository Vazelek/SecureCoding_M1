package hr.ticketmaster.finder.ai.ticketmasterfinderai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TicketSearchForm {
    private String type;
    private String eventName;
    private String venue;
    private String description;
    private String eventDateTimeFromString;
    private String eventDateTimeToString;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
}
