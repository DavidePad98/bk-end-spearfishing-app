package capstonepj_bkend.bkendcpj.repositories;

import capstonepj_bkend.bkendcpj.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    Like findByTicketIdAndUserId(UUID ticketId, UUID userId);
    Like findByPostIdAndUserId(UUID postId, UUID userId);
    Like findByCommentIdAndUserId(UUID commentId, UUID userId);
    List<Like> findByPostId(UUID postId);
    List<Like> findByTicketId(UUID ticketId);
    List<Like> findByCommentId(UUID commentId);
    List<Like> findByUserId(UUID userId);
}
