package com.github.nbreum15.monorepotools.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;

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

    public List<String> getFolderNameParts() {
        return StreamSupport
                .stream(Path.of(getFolderName()).spliterator(), false)
                .map(Path::toString)
                .toList();
    }
}
