package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "POST_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String postTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String postDescription;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(name = "postCategory",nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptCategory postCategory;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Like> likes;

    @Builder
    public Post(String postTitle, String postDescription, PromptCategory postCategory, Prompt prompt) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.createAt = LocalDateTime.now();
        this.postCategory = postCategory;
        this.prompt = prompt;
    }

    public void update(Post post) {
        if (post == null) {
            throw new ApiException(ErrorDefine.NULL_POST_ERROR);
        }
        this.postTitle = post.getPostTitle();
        this.postDescription = post.getPostDescription();
        this.postCategory = post.getPostCategory();
    }

    public Post toEntity(){
        return Post.builder()
                .postTitle(postTitle)
                .postDescription(postDescription)
                .postCategory(postCategory)
                .prompt(prompt)
                .build();
    }

}
