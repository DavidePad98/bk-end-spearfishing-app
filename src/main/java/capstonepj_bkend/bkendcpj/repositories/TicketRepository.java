package capstonepj_bkend.bkendcpj.repositories;

import capstonepj_bkend.bkendcpj.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    public Optional<Ticket> findByTitle(String title);
    @Query("SELECT t FROM Ticket t WHERE t.user_id.id = :userId")
    List<Ticket> findByUserId(UUID userId);

    @Query("SELECT t FROM Ticket t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Ticket> searchByTitle(@Param("query") String query);
}
