package com.github.nbreum15.monorepotools.settings.excludefiles.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Builder
public class ExcludeFilesConfigDTO {
    @Accessors(fluent = true)
    @Builder.Default
    private boolean excludeFilesOnStartUp = false; // feature complete, fully works.
    @Builder.Default
    @Accessors(fluent = true)
    private boolean excludeFilesOnBranchChange = false; // experimental feature
    @Builder.Default
    private Set<String> excludeGlobsForVcs = Set.of();
}
