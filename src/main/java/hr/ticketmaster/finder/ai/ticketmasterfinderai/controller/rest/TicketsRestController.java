package hr.ticketmaster.finder.ai.ticketmasterfinderai.controller.rest;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("rest")
public class TicketsRestController {

    private static List<Ticket> ticketList;


    @GetMapping("/tickets")
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return ticketList.stream()
                .filter(t -> t.getId().compareTo(id) == 0)
                .toList().getFirst();
    }

    @PostMapping("/ticket")
    public void createNewTicket(@RequestBody Ticket ticket) {
        ticketList.add(ticket);
    }

}
