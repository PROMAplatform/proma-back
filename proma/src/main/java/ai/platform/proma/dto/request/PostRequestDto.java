package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostRequestDto(
        @NotNull(message = "postTitle must not be null")
        String postTitle,
        @NotNull(message = "postDescription must not be null")
        String postDescription,
        @NotNull(message = "postCategory must not be null")
        @ValidEnum(enumClass = PromptCategory.class, message = "Invalid  postCategory")
        String postCategory
) {


    public Post toEntity() {
        return Post.builder()
                .postTitle(this.postTitle)
                .postDescription(this.postDescription)
                .postCategory(PromptCategory.fromValue(this.postCategory))
                .build();
    }
}
