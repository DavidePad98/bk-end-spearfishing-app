package capstonepj_bkend.bkendcpj.payloads;

import java.util.UUID;

public record LoginResponseDTO(

        UUID user_id,

        String nickname,

        String name,

        String surname,

        String email,

        String city,

        String social,

        String profileImage

) {
}
