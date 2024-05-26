package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.payloads.LikeResponse;
import capstonepj_bkend.bkendcpj.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService lService;

    @GetMapping("/post/{postId}")
    public List<LikeResponse> getLikesForPost(@PathVariable UUID postId) {
        return lService.getLikesForPost(postId);
    }
    @GetMapping("/ticket/{ticketId}")
    public List<LikeResponse> getLikesForTicket(@PathVariable UUID ticketId) {
        return lService.getLikesForTicket(ticketId);
    }

    @GetMapping("/comment/{commentId}")
    public List<LikeResponse> getLikesForComment(@PathVariable UUID commentId) {
        return lService.getLikesForComment(commentId);
    }

    @GetMapping("/user/{userId}")
    public List<LikeResponse> getLikesForUser(@PathVariable UUID userId) {
        return lService.getLikesForUser(userId);
    }

    @PostMapping("/ticket/{ticketId}")
    public LikeResponse likeTicket(@PathVariable UUID ticketId, @RequestParam UUID userId) {
        return lService.addLikeToTicket(ticketId, userId);
    }

    @PostMapping("/post/{postId}")
    public LikeResponse likePost(@PathVariable UUID postId, @RequestParam UUID userId) {
        return lService.addLikeToPost(postId, userId);
    }

    @PostMapping("/comment/{commentId}")
    public LikeResponse likeComment(@PathVariable UUID commentId, @RequestParam UUID userId) {
        return lService.addLikeToComment(commentId, userId);
    }

    @DeleteMapping("/post/{postId}")
    public void unlikePost(@PathVariable UUID postId, @RequestParam UUID userId) {
        lService.removeLikeToPost(postId, userId);
    }

    @DeleteMapping("/comment/{commentId}")
    public void unlikeComment(@PathVariable UUID commentId, @RequestParam UUID userId) {
        lService.removeLikeToComment(commentId, userId);
    }

    @DeleteMapping("/ticket/{ticketId}")
    public void unlikeTicket(@PathVariable UUID ticketId, @RequestParam UUID userId) {
        lService.removeLikeToTicket(ticketId, userId);
    }
}
