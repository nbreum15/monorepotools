package com.github.nbreum15.monorepotools.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FolderLevelDTO {
    @Builder.Default
    private String folderName = "folder";
    @Builder.Default
    private int level = 1;
}
