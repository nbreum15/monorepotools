package com.github.nbreum15.monorepotools.settings.autocommitmessage;

import com.github.nbreum15.monorepotools.settings.autocommitmessage.dto.AutoCommitMessageConfigDTO;
import com.github.nbreum15.monorepotools.settings.autocommitmessage.mapping.AutoCommitMessageConfigMapper;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.SettingsCategory;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "com.github.nbreum15.monorepotools.settings.autocommitmessage.commitmessage.AutoCommitMessageSettingsState",
        storages = @Storage(value = "MonorepoAutoCommitMessage.xml"),
        category = SettingsCategory.CODE
)
public class AutoCommitMessageSettingsState implements PersistentStateComponent<AutoCommitMessageSettingsState> {

    /**
     * Should the plugin automatically fill in the commit prefix when ticking/unticking changes in VCS?
     */
    public boolean automaticCommitMessage;

    public static AutoCommitMessageSettingsState getInstance(Project project) {
        return project.getService(AutoCommitMessageSettingsState.class);
    }

    public AutoCommitMessageConfigDTO toConfig() {
        return AutoCommitMessageConfigMapper.INSTANCE.map(this);
    }

    @Nullable
    @Override
    public AutoCommitMessageSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AutoCommitMessageSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
