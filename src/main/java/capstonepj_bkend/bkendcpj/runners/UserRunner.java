package capstonepj_bkend.bkendcpj.runners;

import capstonepj_bkend.bkendcpj.payloads.UserDTO;
import capstonepj_bkend.bkendcpj.services.RoleService;
import capstonepj_bkend.bkendcpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRunner implements CommandLineRunner {

    @Autowired
    private RoleService rService;

    @Autowired
    private UserService uService;

    public void run(String[] args) throws Exception {

        if (!rService.existsByRole("ADMIN")) {

            rService.addRole("ADMIN");
        }
        if (!rService.existsByRole("USER")) {
            rService.addRole("USER");
        }

        if (!uService.existsByEmail("admin@admin.admin")) {
            uService.createUser(new UserDTO("admin", "admin", "admin" ,"admin@admin.admin", "password1234", "Napoli"), "ADMIN");
        }
    }
}
