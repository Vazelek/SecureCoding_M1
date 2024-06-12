package hr.ticketmaster.finder.ai.ticketmasterfinderai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private String type;
    private String eventName;
    private String venue;
    private String description;
    private String eventDateTimeString;
    private BigDecimal price;
}
