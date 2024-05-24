package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.EditPostDTO;
import capstonepj_bkend.bkendcpj.payloads.PostDTO;
import capstonepj_bkend.bkendcpj.payloads.TicketDTO;
import capstonepj_bkend.bkendcpj.repositories.PostRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository pRepo;

    @Autowired
    private UserService uService;

    @Autowired
    private TicketService tService;

    @Autowired
    private Cloudinary cloudinary;

    public Post getPostById(UUID id) {
        return pRepo.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
    }

    public Page<Post> getPosts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return pRepo.findAll(pageable);
    }

    public Post createPost(String authorId, String ticketId, String text, MultipartFile image) throws IOException {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("url");
        }

        User foundUser = uService.getUserById(UUID.fromString(authorId));
        Ticket foundTicket = tService.getTicketCoversation(UUID.fromString(ticketId));

        Post post = Post.builder()
                .text(text)
                .urlContent(imageUrl)
                .postCreationDate(LocalDate.now())
                .ticketId(foundTicket)
                .author(foundUser)
                .build();

        return pRepo.save(post);
    }

    public Post updatePostCoversation(UUID id, String text, MultipartFile image) throws IOException {
        Post existingPost = pRepo.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));

        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            existingPost.setUrlContent(imageUrl);
        }

        existingPost.setText(text);
        return pRepo.save(existingPost);
    }

    public void deletePostCoversation(UUID id) {
        pRepo.deleteById(id);
    }

    public List<Post> getPostsByUserId(UUID authorId) {
        return pRepo.findByUserId(authorId);
    }

    public List<Post> findPostsByTicketId(UUID ticketId) {
        return pRepo.findAllPostsByTicketId(ticketId);
    }
}
