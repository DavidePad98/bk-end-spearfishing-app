package capstonepj_bkend.bkendcpj.repositories;

import capstonepj_bkend.bkendcpj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByNicknameOrEmail(String nickname, String email);

    boolean existsByNicknameOrEmail(String nickname, String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role.role <> 'ADMIN' AND (u.name LIKE %:query% OR u.surname LIKE %:query% OR u.nickname LIKE %:query%)")
    List<User> searchByName(@Param("query") String query);
}
