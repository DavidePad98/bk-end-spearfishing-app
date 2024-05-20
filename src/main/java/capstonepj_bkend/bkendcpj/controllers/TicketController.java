package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.EditTicketDTO;
import capstonepj_bkend.bkendcpj.payloads.TicketDTO;
import capstonepj_bkend.bkendcpj.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService tService;

    @GetMapping
    public Page<Ticket> getAllTickets(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort) {
        return tService.getTickets(page, size, sort);
    }

    @GetMapping("/{id}")
    public Ticket getTicketCoversation(@PathVariable UUID id) {
        return tService.getTicketCoversation(id);
    }

    @GetMapping("/title/{title}")
    public Ticket findByTicketTitle(@PathVariable String title) {
        return tService.getTicketByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicketCoversation(@RequestBody TicketDTO ticket) {
        return tService.createTicketCoversation(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable UUID id, @RequestBody @Validated EditTicketDTO ticket, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return tService.updateTicketCoversation(id, ticket);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicketCoversation(@PathVariable UUID id) {
        tService.deleteTicketCoversation(id);
    }

    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsByUserId(@PathVariable UUID userId) {
        return tService.getTicketsByUserId(userId);
    }
}
