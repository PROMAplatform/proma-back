package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.PromptMethod;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "PROMPT_METHOD_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Builder
public class PromptMethods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptMethod promptMethod;

    // --------------------------------------------------------------------

    @OneToMany(mappedBy = "promptMethods", cascade = CascadeType.MERGE)
    private List<Prompt> prompts;

    @OneToMany(mappedBy = "promptMethods", cascade = CascadeType.MERGE)
    private List<Block> Blocks;

    public PromptMethods(Long id, PromptMethod promptMethod, List<Prompt> prompts, List<Block> blocks) {
        this.id = id;
        this.promptMethod = promptMethod;
        this.prompts = prompts;
        Blocks = blocks;
    }
}
