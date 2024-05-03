package capstonepj_bkend.bkendcpj.services;

import capstonepj_bkend.bkendcpj.entities.Role;
import capstonepj_bkend.bkendcpj.exceptions.BadRequestException;
import capstonepj_bkend.bkendcpj.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role getRole(String role) {
        return roleRepository.findByRole(role).orElseThrow(() -> new BadRequestException("Role not found"));
    }

    public Role addRole(String roleString) {
        Role role = new Role(roleString);
        return roleRepository.save(role);
    }

    public boolean existsByRole(String role) {
        return roleRepository.existsByRole(role);
    }
}
