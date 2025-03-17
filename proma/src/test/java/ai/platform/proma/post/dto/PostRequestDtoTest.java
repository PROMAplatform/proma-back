package ai.platform.proma.post.dto;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        // Validator 초기화
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void PostRequestDto의_postTitle은_필수_입력값이다() {
        // Given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postTitle(null)  // postTitle이 null인 경우
                .postDescription("Description")
                .postCategory("예술")
                .build();

        // When
        Set<ConstraintViolation<PostRequestDto>> violations = validator.validate(postRequestDto);

        // Then
        assertThat(violations).hasSize(1); // 유효성 검사에서 실패한 항목이 1개
        assertThat(violations.iterator().next().getMessage()).isEqualTo("postTitle must not be null");
    }

    @Test
    void PostRequestDto의_postDescription은_필수_입력값이다() {
        // Given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postTitle("Title")
                .postDescription(null)  // postDescription이 null인 경우
                .postCategory("예술")
                .build();

        // When
        Set<ConstraintViolation<PostRequestDto>> violations = validator.validate(postRequestDto);

        // Then
        assertThat(violations).hasSize(1); // 유효성 검사에서 실패한 항목이 1개
        assertThat(violations.iterator().next().getMessage()).isEqualTo("postDescription must not be null");
    }

    @Test
    void PostRequestDto의_postCategory는_필수_입력값이다() {
        // Given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postTitle("Title")
                .postDescription("Description")
                .postCategory(null)  // postCategory가 null인 경우
                .build();

        // When
        Set<ConstraintViolation<PostRequestDto>> violations = validator.validate(postRequestDto);

        // Then
        assertThat(violations).hasSize(1); // 유효성 검사에서 실패한 항목이 1개
        assertThat(violations.iterator().next().getMessage()).isEqualTo("postCategory must not be null");
    }

    @Test
    void 잘못된_postCategory값에_대해_예외가_발생해야_한다() {
        // Given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postTitle("Title")
                .postDescription("Description")
                .postCategory("INVALID_CATEGORY")  // 유효하지 않은 카테고리 값
                .build();

        // When
        Set<ConstraintViolation<PostRequestDto>> violations = validator.validate(postRequestDto);

        // Then
        assertThat(violations).hasSize(1); // 유효성 검사에서 실패한 항목이 1개
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid  postCategory");
    }
}

