package hr.ticketmaster.finder.ai.ticketmasterfinderai.controller.rest;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rest")
public class TicketsRestController {

    private static List<Ticket> ticketList = new ArrayList<>();

    @Secured("ROLE_USER")
    @GetMapping("/tickets")
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @Secured("ROLE_USER")
    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return ticketList.stream()
                .filter(t -> t.getId().compareTo(id) == 0)
                .toList().getFirst();
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewTicket(@RequestBody Ticket ticket) {
        ticketList.add(ticket);
    }

}
