package ai.platform.proma.post.dto;

import ai.platform.proma.domain.Post;
import ai.platform.proma.dto.response.SortInfo;

public class SortInfoImpl implements SortInfo {
    private Post post;
    private int likeCount;

    public SortInfoImpl(Post post, int likeCount) {
        this.post = post;
        this.likeCount = likeCount;
    }

    @Override
    public Post getPost() {
        return post;
    }

    @Override
    public int getLikeCount() {
        return likeCount;
    }
}
