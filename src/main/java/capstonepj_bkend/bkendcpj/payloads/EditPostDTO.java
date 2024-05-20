package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditPostDTO(

        @NotBlank(message = "Text required")
        @Size(min = 3, message = "Text must be at least 3 characters")
        String text,
        String urlContent
) {
}
