package com.github.nbreum15.monorepotools.settings.commitmessage.dto;

import com.intellij.util.xmlb.annotations.Tag;
import com.intellij.util.xmlb.annotations.XCollection;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CommitMessageConfigDTO {
    /**
     * If the change is at the root, then this is used.
     */
    @Tag("rootFolder")
    @Builder.Default
    private String rootFolder = "root";
    @Tag("folderLevels")
    @XCollection(style = XCollection.Style.v2)
    @Builder.Default
    private List<FolderLevelDTO> folderLevels = List.of();
    /**
     * Intention is that these are ordered by the user
     */
    @Tag("expandFolders")
    @XCollection(style = XCollection.Style.v2)
    @Builder.Default
    private List<ExpandFolderDTO> expandFolders = List.of();
}
