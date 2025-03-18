package ai.platform.proma.post.repository;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.SortInfo;
import ai.platform.proma.post.dto.SortInfoImpl;
import ai.platform.proma.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ai.platform.proma.domain.enums.Scrap;

import java.util.*;
import java.util.stream.Collectors;

public class FakePostRepository implements PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private final List<Like> likes = new ArrayList<>();
    private final List<Prompt> prompts = new ArrayList<>();
    private static Long idCounter = 0L;
    private final Map<Long, Post> postStore = new HashMap<>();

    //     테스트 데이터를 추가하기 위한 헬퍼 메서드
//    @Override
//    public Post save(Post post) {
//        if (post.getId() == null || post.getId() == 0) {
//            Post newPost = Post.builder()
//                    .id(post.getId())
//                    .postTitle(post.getPostTitle())
//                    .postDescription(post.getPostDescription())
//                    .postCategory(post.getPostCategory())
//                    .prompt(post.getPrompt())
//                    .build();
//            data.add(newPost);
//            return newPost;
//        } else {
//            data.removeIf(existingPost -> Objects.equals(existingPost.getId(), post.getId()));
//            data.add(post);
//            return post;
//        }
//    }
    @Override
    public Post save(Post post) {
        Long id = post.getId() != null && post.getId() > 0 ? post.getId() : ++idCounter;
        Post savedPost = Post.builder()
                .id(id)
                .postTitle(post.getPostTitle())
                .postDescription(post.getPostDescription())
                .postCategory(post.getPostCategory())
                .prompt(post.getPrompt())
                .build();
        postStore.put(id, savedPost);
        return savedPost;
    }


    @Override
    public Page<SortInfo> findAllBySearchKeywordAndCategory(
            String searchKeyword, PromptCategory category, PromptMethods method, Pageable pageable) {
        List<SortInfo> filtered = posts.stream()
                .filter(post -> matchesSearchKeyword(post, searchKeyword))
                .filter(post -> category == null || post.getPostCategory() == category)
                .filter(post -> method == null || getPromptMethod(post) == method)
                .map(post -> new SortInfoImpl(post, (int) getLikeCount(post)))
                .collect(Collectors.toList());

        return paging(filtered, pageable);
    }

    @Override
    public Page<SortInfo> findAllByUserIdAndPostCategoryAndIsScrapShared(
            Long userId, PromptCategory category, PromptMethods method, Pageable pageable) {
        List<SortInfo> filtered = posts.stream()
                .filter(post -> {
                    Prompt prompt = getPrompt(post);
                    return prompt != null && prompt.getUser().getId().equals(userId) &&
                            prompt.getIsScrap() == Scrap.SHARED;
                })
                .filter(post -> category == null || post.getPostCategory() == category)
                .filter(post -> method == null || getPromptMethod(post) == method)
                .map(post -> new SortInfoImpl(post, (int) getLikeCount(post)))
                .collect(Collectors.toList());

        return paging(filtered, pageable);
    }

    @Override
    public Page<SortInfo> findAllByPostIdInAndPostCategory(
            PromptCategory category, PromptMethods method, List<Long> postIds, Pageable pageable) {
        List<SortInfo> filtered = posts.stream()
                .filter(post -> postIds.contains(post.getId()))
                .filter(post -> category == null || post.getPostCategory() == category)
                .filter(post -> method == null || getPromptMethod(post) == method)
                .map(post -> new SortInfoImpl(post, (int) getLikeCount(post)))
                .collect(Collectors.toList());

        return paging(filtered, pageable);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(postStore.get(postId));

    }


    @Override
    public void delete(Post post) {
        if (post != null && post.getId() != null) {
            postStore.remove(post.getId());
        }
    }

    // 헬퍼 메서드
    private boolean matchesSearchKeyword(Post post, String searchKeyword) {
        if (searchKeyword == null) return true;
        return (post.getPostTitle() != null && post.getPostTitle().contains(searchKeyword)) ||
                (post.getPostDescription() != null && post.getPostDescription().contains(searchKeyword));
    }

    private Prompt getPrompt(Post post) {
        return prompts.stream()
                .filter(p -> p.getId().equals(post.getPrompt().getId()))
                .findFirst()
                .orElse(null);
    }

    private PromptMethods getPromptMethod(Post post) {
        Prompt prompt = getPrompt(post);
        return prompt != null ? prompt.getPromptMethods() : null;
    }

    private long getLikeCount(Post post) {
        return likes.stream()
                .filter(like -> like.getPost().getId().equals(post.getId()))
                .count();
    }

    private Page<SortInfo> paging(List<SortInfo> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        List<SortInfo> pagedList = (start < list.size()) ? list.subList(start, end) : Collections.emptyList();
        return new PageImpl<>(pagedList, pageable, list.size());
    }
}
