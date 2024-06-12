package hr.ticketmaster.finder.ai.ticketmasterfinderai.service;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.dto.TicketDTO;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.dto.TicketFormDTO;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketFilter;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<TicketDTO> findAll();
    Optional<TicketDTO> findById(Integer id);
    void save(TicketDTO ticketDTO);
    List<TicketDTO> filterByCriteria(TicketFormDTO ticketFormDTO);
}
