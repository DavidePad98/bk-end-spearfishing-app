package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UserDTO(
        @NotBlank(message = "Nickname is required")
        @Size(min = 3, message = "Nickname must at least 3 characters long")
        String nickname,

        @NotBlank(message = "Name is required")
        @Size(min = 3, message = "Name must at least 3 characters long")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(min = 3, message = "Surname must at least 3 characters long")
        String surname,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must at least 8 characters long")
        String password,

        @NotBlank(message = "City is required")
        @Size(min = 3, message = "City must at least 3 characters long")
        String city

) {
}
