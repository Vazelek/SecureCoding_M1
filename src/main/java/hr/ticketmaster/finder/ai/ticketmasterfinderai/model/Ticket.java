package hr.ticketmaster.finder.ai.ticketmasterfinderai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TICKET")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TicketType type;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "VENUE")
    private String venue;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EVENT_DATE_TIME")
    private LocalDateTime eventDateTime;

    @Column(name = "PRICE")
    private BigDecimal price;

    public Ticket(TicketType type, String eventName, String venue, String description, LocalDateTime eventDateTime,
                  BigDecimal price)
    {
        this.type = type;
        this.eventName = eventName;
        this.venue = venue;
        this.description = description;
        this.eventDateTime = eventDateTime;
        this.price = price;
    }
}
