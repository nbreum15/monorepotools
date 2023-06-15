package com.github.nbreum15.monorepotools.config.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("CommitMessageConfigDTO")
public class CommitMessageConfigDTO {
    /**
     * If the change is at the root, then this is used.
     */
    @Builder.Default
    private String rootFolder = "root";
    @Builder.Default
    private List<FolderLevelDTO> folderLevels = List.of();
    /**
     * Intention is that these are ordered by the user
     */
    @Builder.Default
    private List<ExpandFolderDTO> expandFolders = List.of();
}
