package ai.platform.proma.repository;

import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.security.UserLoginForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("SELECT UserLoginForm(b.socialId, b.role) FROM User b WHERE b.socialId = :socialId AND b.refreshToken IS NOT NULL")
    Optional<UserLoginForm> findUserLoginFormBySocialId(@Param("socialId") String socialId);

    @Query("SELECT UserLoginForm(b.socialId, b.role) FROM User b WHERE b.socialId = :socialId AND b.refreshToken = :refreshToken")
    Optional<UserLoginForm> findUserLoginFormBySocialIdAndRefreshToken(@Param("socialId") String socialId, @Param("refreshToken") String refreshToken);

    Optional<User> findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(@Param("socialId") String socialId);

    User findBySocialIdAndUserLoginMethod(@Param("socialId") String socialId, @Param("userLoginMethod") UserLoginMethod userLoginMethod);
}
