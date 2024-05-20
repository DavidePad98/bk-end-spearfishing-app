package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.entities.Post;
import capstonepj_bkend.bkendcpj.entities.Ticket;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.EditPostDTO;
import capstonepj_bkend.bkendcpj.payloads.PostDTO;
import capstonepj_bkend.bkendcpj.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService pService;

    @GetMapping
    public Page<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sort) {
        return pService.getPosts(page, size, sort);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable UUID id) {
        return pService.getPostById(id);
    }

//    @GetMapping("/text/{title}")
//    public Post findByPostTitle(@PathVariable String title) {
//        return pService.getPostByTitle(title);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPostCoversation(@RequestBody PostDTO post) {
        return pService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePostCoversation(@PathVariable UUID id, @RequestBody @Validated EditPostDTO ticket, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return pService.updatePostCoversation(id, ticket);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostCoversation(@PathVariable UUID id) {
        pService.deletePostCoversation(id);
    }

    @GetMapping("/user/{authorId}")
    public List<Post> getPostsByUserId(@PathVariable UUID authorId) {
        return pService.getPostsByUserId(authorId);
    }

    @GetMapping("/byTicket/{ticketId}")
    public List<Post> getPostsByTicketId(@PathVariable UUID ticketId) {
        return pService.findPostsByTicketId(ticketId);
    }
}
