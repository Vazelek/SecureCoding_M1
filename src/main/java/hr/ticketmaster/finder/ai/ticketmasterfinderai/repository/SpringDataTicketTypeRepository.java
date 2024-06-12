package hr.ticketmaster.finder.ai.ticketmasterfinderai.repository;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketType;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTicketTypeRepository extends JpaRepository<TicketType, Integer> {
    List<TicketType> findByName(String name);
}
