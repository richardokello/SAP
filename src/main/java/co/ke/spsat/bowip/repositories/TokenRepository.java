package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
select t from  Token t inner join Users u on t.user.userId = u.userId
where t.user.userId = :userId and t.loggedOut = false
""")
    List<Token> findAllAccessTokensByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}
