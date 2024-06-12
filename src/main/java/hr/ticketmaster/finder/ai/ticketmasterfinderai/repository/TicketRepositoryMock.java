package hr.ticketmaster.finder.ai.ticketmasterfinderai.repository;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketFilter;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketTypeEnum;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.utils.ConversionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryMock implements TicketRepository {

    private static List<Ticket> ticketList;

    private static final String TICKET_DESCRIPTION = "Rezultat je poznat";

    static {
        ticketList = new ArrayList<>();

        Ticket firstTicket = new Ticket();
        firstTicket.setId(1);
        //firstTicket.setType(TicketTypeEnum.SPORT);
        firstTicket.setEventName("FOOTBALL MATCH: DINAMO ZAGREB - LOKOMOTIVA");
        firstTicket.setVenue("Stadion Maksimir (posljednja utakmica)");
        firstTicket.setDescription(TICKET_DESCRIPTION);
        firstTicket.setEventDateTime(LocalDateTime.now().plusDays(1));
        firstTicket.setPrice(new BigDecimal("20"));

        Ticket secondTicket = new Ticket();
        secondTicket.setId(2);
        //secondTicket.setType(TicketTypeEnum.SPORT);
        secondTicket.setEventName("FOOTBALL MATCH: HAJDUK - LOKOMOTIVA");
        secondTicket.setVenue("Stadion Poljud (posljednja utakmica)");
        secondTicket.setDescription(TICKET_DESCRIPTION);
        secondTicket.setEventDateTime(LocalDateTime.now().plusDays(10));
        secondTicket.setPrice(new BigDecimal("21"));

        Ticket thirdTicket = new Ticket();
        thirdTicket.setId(3);
        //thirdTicket.setType(TicketTypeEnum.SPORT);
        thirdTicket.setEventName("FOOTBALL MATCH: SLAVEN BELUPO - RIJEKA");
        thirdTicket.setVenue("Stadion Gradski (posljednja utakmica)");
        thirdTicket.setDescription(TICKET_DESCRIPTION);
        thirdTicket.setEventDateTime(LocalDateTime.now().plusDays(20));
        thirdTicket.setPrice(new BigDecimal("22"));

        Ticket fourthTicket = new Ticket();
        fourthTicket.setId(4);
        //fourthTicket.setType(TicketTypeEnum.SPORT);
        fourthTicket.setEventName("FOOTBALL MATCH: DINAMO ZAGREB - RUDEŠ");
        fourthTicket.setVenue("Stadion Maksimir (posljednja utakmica)");
        fourthTicket.setDescription(TICKET_DESCRIPTION);
        fourthTicket.setEventDateTime(LocalDateTime.now().plusDays(30));
        fourthTicket.setPrice(new BigDecimal("23"));

        Ticket fifthTicket = new Ticket();
        fifthTicket.setId(5);
        //fifthTicket.setType(TicketTypeEnum.ART);
        fifthTicket.setEventName("NUTCRACKER");
        fifthTicket.setVenue("Hrvatsko narodno kazalište");
        fifthTicket.setDescription("Božićna predstava");
        fifthTicket.setEventDateTime(LocalDateTime.now().plusDays(180));
        fifthTicket.setPrice(new BigDecimal("50"));

        ticketList.add(firstTicket);
        ticketList.add(secondTicket);
        ticketList.add(thirdTicket);
        ticketList.add(fourthTicket);
        ticketList.add(fifthTicket);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketList;
    }

    @Override
    public Optional<Ticket> findById(Integer id) {
        return ticketList.stream()
                .filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Ticket ticket) {
        ticketList.add(ticket);
    }

    @Override
    public List<Ticket> filterByCriteria(TicketFilter ticketFilter) {
        List<Ticket> ticketsList = findAll();

        Optional<TicketTypeEnum> ticketTypOptional = Arrays.stream(TicketTypeEnum.values())
                .filter(tt -> tt.name().equals(ticketFilter.getType()))
                .findFirst();

        if (ticketTypOptional.isPresent()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getType().getName().equals(ticketFilter.getType()))
                    .toList();
        }

        if (!ticketFilter.getEventName().isEmpty()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getEventName().contains(ticketFilter.getEventName()))
                    .toList();
        }

        if (!ticketFilter.getVenue().isEmpty()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getVenue().contains(ticketFilter.getVenue()))
                    .toList();
        }

        if (!ticketFilter.getDescription().isEmpty()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getDescription().contains(ticketFilter.getDescription()))
                    .toList();
        }

        if (!ticketFilter.getEventDateTimeToString().isEmpty()) {
            LocalDateTime localDateTimeTo = LocalDateTime.parse(ticketFilter.getEventDateTimeToString(),
                    ConversionUtils.FORMATTER);

            ticketsList = ticketsList.stream()
                    .filter(t -> t.getEventDateTime().isBefore(localDateTimeTo))
                    .toList();
        }

        if (!ticketFilter.getEventDateTimeFromString().isEmpty()) {
            LocalDateTime localDateTimeFrom = LocalDateTime.parse(ticketFilter.getEventDateTimeFromString(),
                    ConversionUtils.FORMATTER);
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getEventDateTime().isAfter(localDateTimeFrom))
                    .toList();
        }

        if(Optional.ofNullable(ticketFilter.getPriceFrom()).isPresent()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getPrice().compareTo(ticketFilter.getPriceFrom()) >= 0)
                    .toList();
        }

        if(Optional.ofNullable(ticketFilter.getPriceTo()).isPresent()) {
            ticketsList = ticketsList.stream()
                    .filter(t -> t.getPrice().compareTo(ticketFilter.getPriceTo()) <= 0)
                    .toList();
        }

        return ticketsList;
    }
}
