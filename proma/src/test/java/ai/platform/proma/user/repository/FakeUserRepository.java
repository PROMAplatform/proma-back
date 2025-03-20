package ai.platform.proma.user.repository;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.Role;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.security.UserLoginForm;

import java.util.*;

public class FakeUserRepository implements UserRepository {
    private final Map<Long, User> userStore = new HashMap<>();
    private final List<User> data = new ArrayList<>();
    private static Long idCounter = 0L;


    // UserLoginForm 생성 헬퍼 메서드
    private UserLoginForm createUserLoginForm(User user) {
        return new UserLoginForm() {
            @Override
            public String getId() {
                return user.getSocialId();
            }

            @Override
            public Role getRole() {
                return user.getRole();
            }
        };
    }

    @Override
    public Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId) {
        return userStore.values().stream()
                .filter(user -> user.getSocialId().equals(socialId) && user.getRefreshToken() != null)
                .map(this::createUserLoginForm) // 헬퍼 메서드 참조
                .findFirst();
    }

    @Override
    public Optional<UserLoginForm> findBySocialIdAndRefreshToken(String socialId, String refreshToken) {
        return userStore.values().stream()
                .filter(user -> user.getSocialId().equals(socialId) &&
                        refreshToken.equals(user.getRefreshToken()))
                .map(this::createUserLoginForm) // 헬퍼 메서드 참조
                .findFirst();
    }

    @Override
    public Optional<User> findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(String socialId) {
        return userStore.values().stream()
                .filter(user -> user.getSocialId().equals(socialId) &&
                        user.getRefreshToken() != null &&
                        user.getIsLogin())
                .findFirst();
    }

    @Override
    public User findBySocialIdAndUserLoginMethod(String socialId, UserLoginMethod userLoginMethod) {
        return userStore.values().stream()
                .filter(user -> user.getSocialId().equals(socialId) &&
                        user.getUserLoginMethod() == userLoginMethod)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<User> findById(Long userId) {
//        return users.stream()
//                .filter(post -> post.getId().equals(userId))
//                .findFirst();
        return Optional.ofNullable(userStore.get(userId));
    }

//    @Override
//    public User save(User user) {
//        if (user.getId() == null || user.getId() == 0) {
//            User newUser = User.builder()
//                    .id(user.getId())
//                    .userLoginId(user.getUserLoginId())
//                    .userName(user.getUserName())
//                    .userLoginMethod(user.getUserLoginMethod())
//                    .socialId(user.getSocialId())
//                    .build();
//            data.add(newUser);
//            return newUser;
//        } else {
//            data.removeIf(existingPost -> Objects.equals(existingPost.getId(), user.getId()));
//            data.add(user);
//            return user;
//        }
//    }
        @Override
        public User save(User user) {
            Long id = user.getId() != null && user.getId() > 0 ? user.getId() : ++idCounter;
            User savedUser = User.builder()
                    .id(id)
                    .userLoginId(user.getUserLoginId())
                    .userName(user.getUserName())
                    .userLoginMethod(user.getUserLoginMethod())
                    .socialId(user.getSocialId())
                    .build();
            userStore.put(id, savedUser);
            return savedUser;
        }
}