package com.github.nbreum15.monorepotools.config.dto;

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
public class ExpandFolderDTO {
    @Builder.Default
    private List<String> folderParts = List.of("path/to/folder");
}
