package com.github.nbreum15.monorepotools.listeners;

import com.github.nbreum15.monorepotools.settings.excludefiles.ExcludeFilesSettingsState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.BranchChangeListener;
import com.intellij.openapi.vcs.VcsException;
import org.jetbrains.annotations.NotNull;

public class ExcludeFilesBranchChangeListener implements BranchChangeListener {

    private final Project project;

    public ExcludeFilesBranchChangeListener(Project project) {
        this.project = project;
    }

    @Override
    public void branchWillChange(@NotNull String branchName) {
    }

    @Override
    public void branchHasChanged(@NotNull String branchName) {
        if (ExcludeFilesSettingsState.getInstance(project).toConfig().excludeFilesOnBranchChange()) {
            try {
                ExcludeFilesProjectStartupListener.excludeFoldersWithFiles(project);
            } catch (VcsException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
