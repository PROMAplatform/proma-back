package ai.platform.proma.post.domain;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {
    @Test
    void 주어진_postRequestDto에_따라_post가_업데이트되어야_한다() {
        // Given
        PostRequestDto postRequestDto1 = PostRequestDto.builder()
                .postTitle("UpdateTitle")
                .postDescription("UpdateDescription")
                .postCategory("ART")
                .build();

        Prompt prompt = Prompt.builder()
                .id(1L)
                .promptTitle("promptTitle")
                .promptDescription("promptDescription")
                .promptCategory(PromptCategory.IT)
                .build();

        Post post = Post.builder()
                .postTitle("Title")
                .postDescription("Hi")
                .postCategory(prompt.getPromptCategory())
                .prompt(prompt)
                .build();


        // When
        post.update(postRequestDto1.toEntity());

        // Then
        assertThat(post.getPostTitle()).isEqualTo("UpdateTitle");
        assertThat(post.getPostDescription()).isEqualTo("UpdateDescription");
        assertThat(post.getPostCategory()).isEqualTo(PromptCategory.fromValue("ART"));
    }

}
