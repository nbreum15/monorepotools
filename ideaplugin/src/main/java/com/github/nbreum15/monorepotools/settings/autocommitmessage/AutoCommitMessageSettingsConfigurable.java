package com.github.nbreum15.monorepotools.settings.autocommitmessage;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoCommitMessageSettingsConfigurable implements Configurable {
    private final Project project;

    public AutoCommitMessageSettingsConfigurable(Project project) {
        this.project = project;
    }

    private AutoCommitMessageSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Monorepo Auto Commit Message";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AutoCommitMessageSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AutoCommitMessageSettingsState settings = AutoCommitMessageSettingsState.getInstance(project);
        return mySettingsComponent.getAutomaticCommitMessageSelected() != settings.automaticCommitMessage;
    }

    @Override
    public void apply() {
        AutoCommitMessageSettingsState settings = AutoCommitMessageSettingsState.getInstance(project);
        settings.automaticCommitMessage = mySettingsComponent.getAutomaticCommitMessageSelected();
    }

    @Override
    public void reset() {
        AutoCommitMessageSettingsState settings = AutoCommitMessageSettingsState.getInstance(project);
        mySettingsComponent.setAutomaticCommitMessageCheckBox(settings.automaticCommitMessage);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
