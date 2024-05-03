package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostDTO(
        @NotBlank(message = "Text required")
        @Size(min = 3, message = "Text must be at least 3 characters")
        String text,
        String urlContent,
        @NotBlank(message = "Ticket is required")
        String ticketId,
        @NotBlank(message = "Author is required")
        String authorId
) {
}
