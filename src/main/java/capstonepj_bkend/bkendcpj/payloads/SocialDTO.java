package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SocialDTO(
        @NotBlank(message = "Social is required")
        @Size(min = 3, message = "City must at least 3 characters long")
        String social

) {
}
