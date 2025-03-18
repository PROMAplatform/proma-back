package ai.platform.proma.post.service;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.domain.enums.Scrap;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.post.repository.FakePostRepository;
import ai.platform.proma.service.command.post.PostDeletePostService;
import ai.platform.proma.user.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostUpdatePostServiceTest {

    private PostDeletePostService postDeletePostService;
    private FakePostRepository fakePostRepository;
    private FakeUserRepository fakeUserRepository;

    @BeforeEach
    void init() {
        this.fakePostRepository = new FakePostRepository();
        this.fakeUserRepository = new FakeUserRepository();

        postDeletePostService = PostDeletePostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .build();

        User user1 = User.builder()
                .id(1L)
                .userLoginId("user1")
                .userName("user1Name")
                .userLoginMethod(UserLoginMethod.KAKAO)
                .socialId("123")
                .build();

        User user2 = User.builder()
                .id(2L)
                .userLoginId("user2")
                .userName("user2Name")
                .userLoginMethod(UserLoginMethod.GOOGLE)
                .socialId("456")
                .build();

        fakeUserRepository.save(user1);
        System.out.println("Saved user: " + fakeUserRepository.findById(1L).orElse(null));
        fakeUserRepository.save(user2);
        System.out.println("Saved user: " + fakeUserRepository.findById(2L).orElse(null));


        PromptMethods promptMethods = PromptMethods.builder()
                .id(1L)
                .promptMethod(PromptMethod.fromValue("Character"))
                .build();

        Prompt prompt = Prompt.builder()
                .id(1L)
                .promptCategory(PromptCategory.IT)
                .promptDescription("promptDescription1")
                .promptTitle("promptTitle1")
                .promptMethods(promptMethods)
                .isScrap(Scrap.NOTSCRAP)
                .emoji(":)")
                .user(user1)
                .build();

        Post post = Post.builder()
                .id(1L)
                .prompt(prompt)
                .postTitle("postTitle1")
                .postDescription("post1Description1")
                .postCategory(prompt.getPromptCategory())
                .build();

        fakePostRepository.save(post);
        System.out.println("Saved post: " + fakePostRepository.findById(1L).orElse(null));



    }

    @Test
    void deletePost로_게시글을_삭제할_수_있다() {
        // given
        Long userId = 1L; // 게시글 작성자
        Long postId = 1L; // 삭제할 게시글

        // when
        boolean result = postDeletePostService.deletePost(userId, postId);
        // then
        assertThat(result).isTrue();
        assertThat(fakePostRepository.findById(postId)).isEmpty(); // 삭제되었는지 확인
    }

    @Test
    void deletePost는_존재하지_않는_사용자를_입력받으면_에러를_던진다() {
        // given
        Long userId = 999L; // 존재하지 않는 사용자
        Long postId = 1L;

        // when
        ApiException exception = assertThrows(ApiException.class, () -> {
            postDeletePostService.deletePost(userId, postId);
        });

        // then
        assertThat(exception.getError()).isEqualTo(ErrorDefine.USER_NOT_FOUND);
    }

    @Test
    void deletePost는_존재하지_않는_게시글을_입력받으면_에러를_던진다() {
        // given
        Long userId = 1L;
        Long postId = 999L; // 존재하지 않는 게시글

        // when
        ApiException exception = assertThrows(ApiException.class, () -> {
            postDeletePostService.deletePost(userId, postId);
        });

        // then
        assertThat(exception.getError()).isEqualTo(ErrorDefine.POST_NOT_FOUND);
    }

    @Test
    void deletePost는_글의_작성자만_글을_삭제할_수_있다() {
        // given
        Long userId = 2L; // user2가 user1의 게시글을 삭제 시도
        Long postId = 1L;

        // when
        ApiException exception = assertThrows(ApiException.class, () -> {
            postDeletePostService.deletePost(userId, postId);
        });

        // then
        assertThat(exception.getError()).isEqualTo(ErrorDefine.UNAUTHORIZED_USER);
    }

}