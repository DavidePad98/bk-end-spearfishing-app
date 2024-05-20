package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;

public record EditCommentDTO(

        @NotBlank(message = "Text required")
        String text
) {
}
