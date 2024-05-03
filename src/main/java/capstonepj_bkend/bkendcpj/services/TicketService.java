package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.TicketDTO;
import capstonepj_bkend.bkendcpj.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository tRepo;

    @Autowired
    private UserService uService;

//    public List<Post> findPostsByTicketId(UUID ticketId) {
//        return tRepo.findPostsByTicket(ticketId);
//    }

    public Ticket getTicketCoversation(UUID id) {
        return tRepo.findById(id).orElseThrow(() -> new BadRequestException("Ticket not found"));
    }

    public Page<Ticket> getTickets(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return tRepo.findAll(pageable);
    }

    public Ticket createTicketCoversation(TicketDTO tk) {
        User found = uService.getUserById(UUID.fromString(tk.user_id()));
        Ticket newTicket = Ticket.builder()
                .title(tk.title())
                .user_id(found)
                .build();
        return tRepo.save(newTicket);
    }

    public Ticket getTicketByTitle(String title) {
        return tRepo.findByTitle(title).orElseThrow(() -> new BadRequestException("Ticket not found"));
    }

    public void deleteTicketCoversation(UUID id) {
        tRepo.deleteById(id);
    }

    public Ticket updateTicketCoversation(UUID id, TicketDTO tk) {
        Ticket existingTicket = tRepo.findById(id).orElseThrow(() -> new BadRequestException("Ticket not found"));
//        if (tRepo.existByTitle(tk.title()) && !existingTicket.getTitle().equals(tk.title())) {
//            throw new BadRequestException("Ticket title already in use");
//        }
        existingTicket.setTitle(tk.title());
        return tRepo.save(existingTicket);
    }
}
