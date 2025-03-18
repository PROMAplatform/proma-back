package ai.platform.proma.repository;

import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.security.UserLoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId) {
        return userJpaRepository.findUserLoginFormBySocialId(socialId);
    }

    @Override
    public Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId, String refreshToken) {
        return userJpaRepository.findUserLoginFormBySocialIdAndRefreshToken(socialId, refreshToken);
    }

    @Override
    public Optional<User> findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(String socialId) {
        return userJpaRepository.findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(socialId);
    }

    @Override
    public User findBySocialIdAndUserLoginMethod(String socialId, UserLoginMethod userLoginMethod) {
        return userJpaRepository.findBySocialIdAndUserLoginMethod(socialId, userLoginMethod);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}