package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.Role;
import ai.platform.proma.domain.enums.UserLoginMethod;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "USER_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userLoginId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String socialId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserLoginMethod userLoginMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    private Boolean isLogin;

    @Column(nullable = false)
    private Boolean userOngoing;

    // --------------------------------------------------------------------

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Prompt> prompts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Block> blocks;

    @Builder
    public User(Long id, String userLoginId, String userName, UserLoginMethod userLoginMethod, String socialId) {
        this.id = id;
        this.userLoginId = userLoginId;
        this.userName = userName;
        this.userLoginMethod = userLoginMethod;
        this.createAt = LocalDate.now();
        this.userOngoing = true;
        this.isLogin = true;
        this.refreshToken = null;
        this.socialId = socialId;
        this.role = Role.USER;
    }

    public static User toEntity(String userLoginId, String userName, UserLoginMethod userLoginMethod, String socialId){
        return User.builder()
                .userLoginId(userLoginId)
                .userName(userName)
                .userLoginMethod(userLoginMethod)
                .socialId(socialId)
                .build();
    }

    public void signIn(String refreshToken) {
        this.refreshToken = refreshToken;
        this.isLogin = true;
    }

    public void signOut(){
        this.refreshToken = null;
        this.isLogin = false;
    }

    public void resign(){
        this.userOngoing = false;
    }

}
