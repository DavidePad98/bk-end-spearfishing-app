package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TicketDTO(
        @NotBlank(message = "Title is required")
        @Size(min = 3, message = "Title at least 3 characters long")
        String title,

        @NotBlank(message = "User is required")
        String user_id
) {
}
