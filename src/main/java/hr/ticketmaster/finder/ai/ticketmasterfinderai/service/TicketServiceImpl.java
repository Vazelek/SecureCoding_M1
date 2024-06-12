package hr.ticketmaster.finder.ai.ticketmasterfinderai.service;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketType;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.repository.SpringDataTicketRepository;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.repository.SpringDataTicketTypeRepository;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.utils.ConversionUtils;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.dto.TicketDTO;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.dto.TicketFormDTO;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    //private TicketRepository ticketRepository;
    private SpringDataTicketRepository ticketRepository;
    private SpringDataTicketTypeRepository ticketTypeRepository;

    @Override
    public List<TicketDTO> findAll() {
        return ticketRepository.findAll()
                .stream().map(this::convertTicketToTicketDto)
                .toList();
    }

    @Override
    public Optional<TicketDTO> findById(Integer id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.map(this::convertTicketToTicketDto);
    }

    @Override
    public void save(TicketDTO ticketDTO) {
        ticketRepository.save(convertTicketDtoToTicket(ticketDTO));
    }

    @Override
    public List<TicketDTO> filterByCriteria(TicketFormDTO ticketFormDTO) {

        Ticket filterTicket = new Ticket();

        String ticketTypeName = ticketFormDTO.getType();
        List<TicketType> ticketTypeList = ticketTypeRepository.findByName(ticketTypeName);

        filterTicket.setType(ticketTypeList.getFirst());

        Example<Ticket> example = Example.of(filterTicket);

        return ticketRepository.findAll(example)
                .stream()
                .map(this::convertTicketToTicketDto)
                .toList();

                /*
        return ticketRepository.filterByCriteria(convertTicketDtoToTicketFilter(ticketFormDTO))
                .stream()
                .map(this::convertTicketToTicketDto)
                .toList();

                 */
    }

    private TicketFilter convertTicketDtoToTicketFilter(TicketFormDTO ticketFormDTO) {
        return new TicketFilter(
                ticketFormDTO.getType(),
                ticketFormDTO.getEventName(),
                ticketFormDTO.getVenue(),
                ticketFormDTO.getDescription(),
                ticketFormDTO.getEventDateTimeFromString(),
                ticketFormDTO.getEventDateTimeToString(),
                ticketFormDTO.getPriceFrom(),
                ticketFormDTO.getPriceTo());
    }

    private Ticket convertTicketDtoToTicket(TicketDTO ticketDTO) {

        TicketType ticketType = new TicketType();
        ticketType.setName(ticketDTO.getType());

        return new Ticket(ticketType,
                            ticketDTO.getEventName(),
                            ticketDTO.getVenue(),
                            ticketDTO.getDescription(),
                            LocalDateTime.parse(ticketDTO.getEventDateTimeString(), ConversionUtils.FORMATTER),
                            ticketDTO.getPrice()
                );
    }

    private TicketDTO convertTicketToTicketDto(Ticket ticket) {
        return new TicketDTO(ticket.getType().getName(),
                                ticket.getEventName(),
                                ticket.getVenue(),
                                ticket.getDescription(),
                                ticket.getEventDateTime().format(ConversionUtils.FORMATTER),
                                ticket.getPrice()
        );
    }
}
