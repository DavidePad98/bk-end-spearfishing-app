package capstonepj_bkend.bkendcpj.controllers;

import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.ProfileImageUrlDTO;
import capstonepj_bkend.bkendcpj.payloads.SocialDTO;
import capstonepj_bkend.bkendcpj.payloads.UserDTO;
import capstonepj_bkend.bkendcpj.repositories.UserRepository;
import capstonepj_bkend.bkendcpj.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService uService;

    @Autowired
    private UserRepository uRepo;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sort) {
        return uService.getUsers(page, size, sort);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        return uService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id,
                           @RequestPart String nickname,
                           @RequestPart String name,
                           @RequestPart String surname,
                           @RequestPart String email,
                           @RequestPart String password,
                           @RequestPart String city,
                           @RequestPart(required = false) String social,
                           @RequestPart(required = false) MultipartFile profileImage,
                           BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return uService.updateUser(id, nickname, name, surname, email, password, city, social, profileImage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable UUID id) {
        uService.deleteUser(id);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String query) {
        return uRepo.searchByName(query);
    }
}
