package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.UnauthorizedException;
import capstonepj_bkend.bkendcpj.payloads.JWTDTO;
import capstonepj_bkend.bkendcpj.payloads.LoginAuthDTO;
import capstonepj_bkend.bkendcpj.payloads.UserDTO;
import capstonepj_bkend.bkendcpj.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService uService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public JWTDTO login(LoginAuthDTO loginAuthDTO) {
        User user = uService.getUserByNicknameOrEmail(loginAuthDTO.nicknameOrEmail());
        if (user == null || !passwordEncoder.matches(loginAuthDTO.password(), user.getPassword())) {
            throw new UnauthorizedException("Credentials not valid. Try login again");
        }
        String token = jwtTools.generateToken(user);
        UserDTO uDTO = new UserDTO(
                user.getNickname(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getCity()
//                user.getSocial(),
//                user.getProfileImage()
                );
        return new JWTDTO(token, uDTO);
    }

}
