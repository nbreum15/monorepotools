package com.github.nbreum15.monorepotools.settings.commitmessage;

import com.github.nbreum15.monorepotools.settings.commitmessage.dto.CommitMessageConfigDTO;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.SettingsCategory;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "com.github.nbreum15.monorepotools.settings.commitmessage.CommitMessageSettingsState",
        storages = @Storage(value = "MonorepoCommitMessage.xml"),
        category = SettingsCategory.CODE
)
public class CommitMessageSettingsState implements PersistentStateComponent<CommitMessageSettingsState> {

    @Tag("config")
    public CommitMessageConfigDTO config = CommitMessageConfigDTO.builder().build();

    public static CommitMessageSettingsState getInstance(Project project) {
        return project.getService(CommitMessageSettingsState.class);
    }

    @Nullable
    @Override
    public CommitMessageSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull CommitMessageSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
