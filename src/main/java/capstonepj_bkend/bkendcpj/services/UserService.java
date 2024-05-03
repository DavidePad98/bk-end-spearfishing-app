package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Role;
import capstonepj_bkend.bkendcpj.entities.User;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.payloads.SocialDTO;
import capstonepj_bkend.bkendcpj.payloads.UserDTO;
import capstonepj_bkend.bkendcpj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private RoleService rService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(UUID id) {
        return uRepo.findById(id).orElseThrow(() -> new BadRequestException("User not found with id: " + id));
    }

    public Page<User> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return uRepo.findAll(pageable);
    }

    public User createUser(UserDTO uDTO) {
        if (uRepo.existsByNicknameOrEmail(uDTO.nickname(), uDTO.email())) {
            throw new BadRequestException("Nickname and email already in use");
        } else if (uRepo.existsByNickname(uDTO.nickname())) {
            throw new BadRequestException("Nickname already in use");
        } else if (uRepo.existsByEmail(uDTO.email())) {
            throw new BadRequestException("Email already in use");
        }
        User newUser = new User();
        Role role = rService.getRole("USER");
        newUser.setNickname(uDTO.nickname());
        newUser.setName(uDTO.name());
        newUser.setSurname(uDTO.surname());
        newUser.setEmail(uDTO.email());
        newUser.setPassword(passwordEncoder.encode(uDTO.password()));
        newUser.setCity(uDTO.city());
        newUser.setRole(role);
        newUser.setRegistrationDate(LocalDateTime.now());
//        newUser.setSocial(uDTO.social());
//        newUser.setProfileImage(uDTO.profileImage());

        return uRepo.save(newUser);
    }

    public User createUser(UserDTO uDTO, String roleString) {
        if (uRepo.existsByNicknameOrEmail(uDTO.nickname(), uDTO.email())) {
            throw new BadRequestException("Nickname and email already in use");
        } else if (uRepo.existsByNickname(uDTO.nickname())) {
            throw new BadRequestException("Nickname already in use");
        } else if (uRepo.existsByEmail(uDTO.email())) {
            throw new BadRequestException("Email already in use");
        }
        User newUser = new User();
        Role role = rService.getRole(roleString);
        newUser.setNickname(uDTO.nickname());
        newUser.setName(uDTO.name());
        newUser.setSurname(uDTO.surname());
        newUser.setEmail(uDTO.email());
        newUser.setPassword(passwordEncoder.encode(uDTO.password()));
        newUser.setCity(uDTO.city());
        newUser.setRole(role);
        newUser.setRegistrationDate(newUser.getRegistrationDate());
//        newUser.setSocial(uDTO.social());
//        newUser.setProfileImage(uDTO.profileImage());

        return uRepo.save(newUser);
    }

    public User updateUser(UUID id, UserDTO uDTO) {
        User existingUser = uRepo.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        if (uRepo.existsByNicknameOrEmail(uDTO.nickname(), uDTO.email()) && !existingUser.getNickname().equals(uDTO.nickname()) && !existingUser.getEmail().equals(uDTO.email())) {
            throw new BadRequestException("Nickname and email already in use");
        } else if (uRepo.existsByNickname(uDTO.nickname()) && !existingUser.getNickname().equals(uDTO.nickname())) {
            throw new BadRequestException("Nickname already in use");
        } else if (uRepo.existsByEmail(uDTO.email()) && !existingUser.getEmail().equals(uDTO.email())) {
            throw new BadRequestException("Email already in use");
        }
        existingUser.setNickname(uDTO.nickname());
        existingUser.setName(uDTO.name());
        existingUser.setSurname(uDTO.surname());
        existingUser.setEmail(uDTO.email());
        existingUser.setPassword(passwordEncoder.encode(uDTO.password()));
        existingUser.setCity(uDTO.city());
//        existingUser.setSocial(uDTO.social());
//        existingUser.setProfileImage(uDTO.profileImage());

        return uRepo.save(existingUser);
    }

    public User uploadSocial(UUID id, SocialDTO socialDTO){
        User existingUser = uRepo.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        existingUser.setSocial(socialDTO.social());
        return uRepo.save(existingUser);
    }

    public User getUserByNicknameOrEmail(String nickOrEmail) {
        return uRepo.findByNicknameOrEmail(nickOrEmail, nickOrEmail).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public boolean existsByNicknameOrEmail(String nickOrEmail) {
        return uRepo.existsByNicknameOrEmail(nickOrEmail, nickOrEmail);
    }

    public boolean existsByNickname(String nickname) {
        return uRepo.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email) {
        return uRepo.existsByEmail(email);
    }

    public void deleteUser(UUID id) {
        uRepo.deleteById(id);
    }
}
