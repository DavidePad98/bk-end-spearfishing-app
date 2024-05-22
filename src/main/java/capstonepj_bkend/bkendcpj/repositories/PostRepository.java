package capstonepj_bkend.bkendcpj.repositories;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.author.id = :author")
    List<Post> findByUserId(UUID author);

    @Query(value = "SELECT p FROM Post p WHERE p.ticketId.id = :ticketId")
    List<Post> findAllPostsByTicketId(@Param("ticketId") UUID ticketId);

    @Query("SELECT p FROM Post p WHERE LOWER(p.text) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchByText(@Param("query") String query);
}
