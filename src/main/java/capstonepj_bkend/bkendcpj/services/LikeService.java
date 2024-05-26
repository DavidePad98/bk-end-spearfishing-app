package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.*;
import capstonepj_bkend.bkendcpj.payloads.LikeResponse;
import capstonepj_bkend.bkendcpj.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository lRepo;

    @Autowired
    private UserService uService;

    @Autowired
    private TicketService tService;

    @Autowired
    private PostService pService;

    @Autowired
    private CommentService cService;

    public List<LikeResponse> getLikesForTicket(UUID ticketId) {
        List<Like> likes = lRepo.findByTicketId(ticketId);
        return likes.stream()
                .map(like -> new LikeResponse(
                        like.getId().toString(),
                        like.getUser().getId().toString(),
                        like.getTicket() != null ? like.getTicket().getId().toString() : null,
                        like.getPost() != null ? like.getPost().getId().toString() : null,
                        like.getComment() != null ? like.getComment().getId().toString() : null
                ))
                .collect(Collectors.toList());
    }

    public LikeResponse addLikeToTicket(UUID ticketId, UUID userId) {
        Ticket ticket = tService.getTicketCoversation(ticketId);
        User user = uService.getUserById(userId);
        Like like = new Like();
        like.setUser(user);
        like.setTicket(ticket);
        lRepo.save(like);
        return new LikeResponse(
                like.getId().toString(),
                like.getUser().getId().toString(),
                like.getTicket() != null ? like.getTicket().getId().toString() : null,
                null, null
        );
    }

    public void removeLikeToTicket(UUID ticketId, UUID userId) {
        Like like = lRepo.findByTicketIdAndUserId(ticketId, userId);
        if(like != null) {
            lRepo.delete(like);
        }
    }

    public List<LikeResponse> getLikesForPost(UUID postId) {
        List<Like> likes = lRepo.findByPostId(postId);
        return likes.stream()
                .map(like -> new LikeResponse(
                        like.getId().toString(),
                        like.getUser().getId().toString(),
                        like.getTicket() != null ? like.getTicket().getId().toString() : null,
                        like.getPost() != null ? like.getPost().getId().toString() : null,
                        like.getComment() != null ? like.getComment().getId().toString() : null
                ))
                .collect(Collectors.toList());
    }

    public LikeResponse addLikeToPost(UUID postId, UUID userId) {
        Post post = pService.getPostById(postId);
        User user = uService.getUserById(userId);
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        lRepo.save(like);
        return new LikeResponse(
                like.getId().toString(),
                like.getUser().getId().toString(),
                null,
                like.getPost() != null ? like.getPost().getId().toString() : null,
                null
        );
    }

    public void removeLikeToPost(UUID postId, UUID userId) {
        Like like = lRepo.findByPostIdAndUserId(postId, userId);
        if(like != null) {
            lRepo.delete(like);
        }
    }

    public List<LikeResponse> getLikesForComment(UUID commentId) {
        List<Like> likes = lRepo.findByCommentId(commentId);
        return likes.stream()
                .map(like -> new LikeResponse(
                        like.getId().toString(),
                        like.getUser().getId().toString(),
                        like.getTicket() != null ? like.getTicket().getId().toString() : null,
                        like.getPost() != null ? like.getPost().getId().toString() : null,
                        like.getComment() != null ? like.getComment().getId().toString() : null
                ))
                .collect(Collectors.toList());
    }


    public LikeResponse addLikeToComment(UUID commentId, UUID userId) {
        Comment comment = cService.getCommentById(commentId);
        User user = uService.getUserById(userId);
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        lRepo.save(like);
        return new LikeResponse(
                like.getId().toString(),
                like.getUser().getId().toString(),
                null,
                null,
                like.getComment() != null ? like.getComment().getId().toString() : null
        );
    }

    public void removeLikeToComment(UUID commentId, UUID userId) {
        Like like = lRepo.findByCommentIdAndUserId(commentId, userId);
        if(like != null) {
            lRepo.delete(like);
        }
    }

    public List<LikeResponse> getLikesForUser(UUID userId) {
        List<Like> likes = lRepo.findByUserId(userId);
        return likes.stream()
                .map(like -> new LikeResponse(
                        like.getId().toString(),
                        like.getUser().getId().toString(),
                        like.getTicket() != null ? like.getTicket().getId().toString() : null,
                        like.getPost() != null ? like.getPost().getId().toString() : null,
                        like.getComment() != null ? like.getComment().getId().toString() : null
                ))
                .collect(Collectors.toList());
    }
}
