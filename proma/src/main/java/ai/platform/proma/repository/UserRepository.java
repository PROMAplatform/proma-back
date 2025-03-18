package ai.platform.proma.repository;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.security.UserLoginForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

public interface UserRepository {
    Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId);

    Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId, String refreshToken);

    Optional<User> findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(String socialId);

    User findBySocialIdAndUserLoginMethod(String socialId, UserLoginMethod userLoginMethod);

    Optional<User> findById(Long userId);

    User save(User user);
}