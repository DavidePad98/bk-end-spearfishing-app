package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Comment;
import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.CommentDTO;
import capstonepj_bkend.bkendcpj.payloads.EditCommentDTO;
import capstonepj_bkend.bkendcpj.payloads.PostDTO;
import capstonepj_bkend.bkendcpj.repositories.CommentRepository;
import capstonepj_bkend.bkendcpj.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository cRepo;

    @Autowired
    private UserService uService;

    @Autowired
    private PostService pService;

    public Comment getCommentById(UUID id) {
        return cRepo.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
    }

    public Page<Comment> getComments(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return cRepo.findAll(pageable);
    }

    public Comment createComment(CommentDTO post) {
        User found = uService.getUserById(UUID.fromString(post.authorId()));
        Post foundPost = pService.getPostById(UUID.fromString(post.postId()));
        Comment newComment = Comment.builder()
                .text(post.text())
                .commentCreationDate(LocalDate.now())
                .post(foundPost)
                .author(found)
                .build();
        return cRepo.save(newComment);
    }

    public void deleteCommentCoversation(UUID id) {
        cRepo.deleteById(id);
    }

    public Comment updateCommentCoversation(UUID id, EditCommentDTO tk) {
        Comment existingComment = cRepo.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        existingComment.setText(tk.text());
        return cRepo.save(existingComment);
    }

    public List<Comment> findAllCommentsByUserId(UUID userId) {
        return cRepo.findAllByAuthorId(userId);
    }

    public List<Comment> findAllCommentsByPostId(UUID postId) {
        return cRepo.findAllByPostId(postId);
    }
}
