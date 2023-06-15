package com.github.nbreum15.monorepotools.settings.commitmessage.dto;

import com.intellij.util.xmlb.annotations.Tag;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FolderLevelDTO {
    @Tag("folderName")
    @Builder.Default
    private String folderName = "folder";
    @Tag("level")
    @Builder.Default
    private int level = 1;
}
