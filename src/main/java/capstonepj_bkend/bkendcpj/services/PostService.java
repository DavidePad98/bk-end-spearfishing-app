package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.EditPostDTO;
import capstonepj_bkend.bkendcpj.payloads.PostDTO;
import capstonepj_bkend.bkendcpj.payloads.TicketDTO;
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
public class PostService {

    @Autowired
    private PostRepository pRepo;

    @Autowired
    private UserService uService;

    @Autowired
    private TicketService tService;

    public Post getPostById(UUID id) {
        return pRepo.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
    }

    public Page<Post> getPosts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return pRepo.findAll(pageable);
    }

    public Post createPost(PostDTO post) {
        User found = uService.getUserById(UUID.fromString(post.authorId()));
        Ticket foundTicket = tService.getTicketCoversation(UUID.fromString(post.ticketId()));
        Post newPost = Post.builder()
                .text(post.text())
                .urlContent(post.urlContent())
                .postCreationDate(LocalDate.now())
                .ticketId(foundTicket)
                .author(found)
                .build();
        return pRepo.save(newPost);
    }

    public void deletePostCoversation(UUID id) {
        pRepo.deleteById(id);
    }

    public Post updatePostCoversation(UUID id, EditPostDTO tk) {
        Post existingPost = pRepo.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        existingPost.setText(tk.text());
        existingPost.setUrlContent(tk.urlContent());
        return pRepo.save(existingPost);
    }

    public List<Post> getPostsByUserId(UUID authorId) {
        return pRepo.findByUserId(authorId);
    }

    public List<Post> findPostsByTicketId(UUID ticketId) {
        return pRepo.findAllPostsByTicketId(ticketId);
    }
}
