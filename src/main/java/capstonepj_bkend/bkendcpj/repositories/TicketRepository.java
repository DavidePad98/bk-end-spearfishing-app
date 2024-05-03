package capstonepj_bkend.bkendcpj.repositories;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    public Optional<Ticket> findByTitle(String title);
//    boolean existByTitle(String title);
//    List<Post> findPostsByTicket(UUID ticket_id);
}