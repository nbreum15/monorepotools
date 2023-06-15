package com.github.nbreum15.monorepotools.settings.excludefiles;

import com.github.nbreum15.monorepotools.settings.excludefiles.dto.ExcludeFilesConfigDTO;
import com.github.nbreum15.monorepotools.settings.excludefiles.mapping.ExcludeFilesConfigMapper;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.SettingsCategory;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Stores application-specific settings for excluding files.
 * These are not intended to be shared in a project.
 */
@State(
        name = "com.github.nbreum15.monorepotools.settings.excludefiles.ExcludeFilesAppSettingsState",
        storages = @Storage(value = "MonorepoExcludeFiles.xml"),
        category = SettingsCategory.CODE
)
public class ExcludeFilesSettingsState implements PersistentStateComponent<ExcludeFilesSettingsState> {
    // exclude files settings
    public boolean excludeFilesOnStartUp = false; // feature complete, fully works.
    public boolean excludeFilesOnBranchChange = false; // experimental feature
    public String excludeGlobsForVcs = "";

    public static ExcludeFilesSettingsState getInstance(Project project) {
        return project.getService(ExcludeFilesSettingsState.class);
    }

    public ExcludeFilesConfigDTO toConfig() {
        return ExcludeFilesConfigMapper.INSTANCE.map(this);
    }

    @Override
    public @Nullable ExcludeFilesSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ExcludeFilesSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
