package ai.platform.proma.repository;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.SortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Page<SortInfo> findAllBySearchKeywordAndCategory(String searchKeyword,
                                                     PromptCategory category,
                                                     PromptMethods method,
                                                     Pageable pageable);

    Page<SortInfo> findAllByUserIdAndPostCategoryAndIsScrapShared(Long userId,
                                                                  PromptCategory category,
                                                                  PromptMethods method,
                                                                  Pageable pageable);

    Page<SortInfo> findAllByPostIdInAndPostCategory(PromptCategory category,
                                                    PromptMethods method,
                                                    List<Long> postIds,
                                                    Pageable pageable);

    Optional<Post> findById(Long postId);

    Post save(Post post);

    void delete(Post post);
}
