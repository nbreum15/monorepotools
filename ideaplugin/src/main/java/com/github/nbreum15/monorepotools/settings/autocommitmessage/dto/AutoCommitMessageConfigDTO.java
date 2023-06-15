package com.github.nbreum15.monorepotools.settings.autocommitmessage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Builder
public class AutoCommitMessageConfigDTO {
    @Accessors(fluent = true)
    private boolean automaticCommitMessage;
}
