package com.github.nbreum15.monorepotools.settings.commitmessage;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommitMessageSettingsConfigurable implements Configurable {
    private final Project project;

    public CommitMessageSettingsConfigurable(Project project) {
        this.project = project;
    }

    private CommitMessageSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Monorepo Commit Message";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new CommitMessageSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        CommitMessageSettingsState settings = CommitMessageSettingsState.getInstance(project);
        boolean modified = !mySettingsComponent.getRootFolderText().equals(settings.config.getRootFolder());
        modified |= !mySettingsComponent.getFolderLevels().equals(settings.config.getFolderLevels());
        modified |= !mySettingsComponent.getExpandFolders().equals(settings.config.getExpandFolders());
        return modified;
    }

    @Override
    public void apply() {
        CommitMessageSettingsState settings = CommitMessageSettingsState.getInstance(project);
        settings.config.setRootFolder(mySettingsComponent.getRootFolderText());
        settings.config.setFolderLevels(mySettingsComponent.getFolderLevels());
        settings.config.setExpandFolders(mySettingsComponent.getExpandFolders());
    }

    @Override
    public void reset() {
        CommitMessageSettingsState settings = CommitMessageSettingsState.getInstance(project);
        mySettingsComponent.setRootFolderText(settings.config.getRootFolder());
        mySettingsComponent.setFolderLevels(settings.config.getFolderLevels());
        mySettingsComponent.setExpandFolders(settings.config.getExpandFolders());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
