package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ProfileImageUrlDTO(
        @NotBlank(message = "Profile image url is required")
        @URL(message = "Invalid url")
        String profileImage
) {
}
