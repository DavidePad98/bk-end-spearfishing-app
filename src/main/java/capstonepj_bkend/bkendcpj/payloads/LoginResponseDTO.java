package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record LoginResponseDTO(

        UUID user_id,

        String nickname,

        String name,

        String surname,

        String email,

        String password,

        String city,

        String social,

        String profileImage

) {
}
