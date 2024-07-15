package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Roles;
import co.ke.spsat.bowip.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    @Query("SELECT u FROM Users u JOIN u.roles r WHERE u.userId = :userId AND u.activated = true AND r.roleName = :roleName")
    Optional<Users>findByUserIdAndActivatedAndRolesIs(@Param("userId")Long userId,   @Param("roleName")String roles);

    Optional<Users> findByUserId(Long userId);
}
