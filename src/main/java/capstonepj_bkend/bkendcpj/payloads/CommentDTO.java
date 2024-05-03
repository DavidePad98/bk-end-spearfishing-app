package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;

public record CommentDTO(
        @NotBlank(message = "Text required")
        String text,
        @NotBlank(message = "PostId is required")
        String postId,
        @NotBlank(message = "AuthorId is required")
        String authorId
) {
}
