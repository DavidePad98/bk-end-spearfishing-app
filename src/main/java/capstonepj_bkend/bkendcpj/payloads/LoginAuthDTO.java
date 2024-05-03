package capstonepj_bkend.bkendcpj.payloads;

import jakarta.validation.constraints.NotBlank;

public record LoginAuthDTO(
        @NotBlank
        String nicknameOrEmail,
        @NotBlank
        String password) {
}
