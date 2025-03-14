package ai.platform.proma.repository;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.SortInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository{

    private final PostJpaRepository postJpaRepository;

    @Override
    public Page<SortInfo> findAllBySearchKeywordAndCategory(String searchKeyword, PromptCategory category, PromptMethods method, Pageable pageable) {
        return postJpaRepository.findAllBySearchKeywordAndCategory(searchKeyword, category, method, pageable);
    }

    @Override
    public Page<SortInfo> findAllByUserIdAndPostCategoryAndIsScrapShared(Long userId, PromptCategory category, PromptMethods method, Pageable pageable) {
        return postJpaRepository.findAllByUserIdAndPostCategoryAndIsScrapShared(userId, category, method, pageable);
    }

    @Override
    public Page<SortInfo> findAllByPostIdInAndPostCategory(PromptCategory category, PromptMethods method, List<Long> postIds, Pageable pageable) {
        return postJpaRepository.findAllByPostIdInAndPostCategory(category, method, postIds, pageable);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(Post::toEntity);
    }

}
