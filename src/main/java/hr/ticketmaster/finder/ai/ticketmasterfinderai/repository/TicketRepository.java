package hr.ticketmaster.finder.ai.ticketmasterfinderai.repository;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketFilter;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    List<Ticket> findAll();
    Optional<Ticket> findById(Integer id);
    void save(Ticket ticket);
    List<Ticket> filterByCriteria(TicketFilter ticketFilter);
}
