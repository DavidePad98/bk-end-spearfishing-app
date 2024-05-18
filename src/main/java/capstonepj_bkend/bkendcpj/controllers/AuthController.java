package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.exceptions.UnauthorizedException;
import capstonepj_bkend.bkendcpj.payloads.JWTDTO;
import capstonepj_bkend.bkendcpj.payloads.LoginAuthDTO;
import capstonepj_bkend.bkendcpj.payloads.LoginResponseDTO;
import capstonepj_bkend.bkendcpj.payloads.UserDTO;
import capstonepj_bkend.bkendcpj.security.JWTTools;
import capstonepj_bkend.bkendcpj.services.AuthService;
import capstonepj_bkend.bkendcpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService uService;
    @Autowired
    private JWTTools jwtTools;

    @PostMapping("login")
    public JWTDTO login(@RequestBody @Validated LoginAuthDTO loginAuthDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return authService.login(loginAuthDTO);
    }


    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated UserDTO userDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return uService.createUser(userDTO);
    }

    @GetMapping("/me")
    public LoginResponseDTO getUserProfile(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("No token provided or token is invalid.");
        }

        String token = authorization.substring(7);
        jwtTools.validateToken(token);  // Validates the JWT token

        String userId = jwtTools.getSubjectFromToken(token); // Extracts user ID from token
        User user = uService.getUserById(UUID.fromString(userId));

        return new LoginResponseDTO(
                user.getId(),
                user.getNickname(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getCity(),
                user.getSocial(),
                user.getProfileImage()
        );
}}
