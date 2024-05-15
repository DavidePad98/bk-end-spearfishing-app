package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.entities.Comment;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.CommentDTO;
import capstonepj_bkend.bkendcpj.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService cService;

    @GetMapping
    public Page<Comment> getAllComments(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sort) {
        return cService.getComments(page, size, sort);
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable UUID id) {
        return cService.getCommentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createCommen(@RequestBody CommentDTO ticket) {
        return cService.createComment(ticket);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable UUID id, @RequestBody @Validated CommentDTO ticket, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return cService.updateCommentCoversation(id, ticket);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentCoversation(@PathVariable UUID id) {
        cService.deleteCommentCoversation(id);
    }

    @GetMapping("/user/{id}")
    public List<Comment> getAllCommentsByAuthorId(@PathVariable UUID id){
        return cService.findAllCommentsByUserId(id);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getAllCommentsByPostId(@PathVariable UUID postId) {
        return cService.findAllCommentsByPostId(postId);
    }
}
