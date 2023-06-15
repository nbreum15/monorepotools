package com.github.nbreum15.monorepotools.settings.excludefiles;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ExcludeFilesSettingsConfigurable implements Configurable {
    private final Project project;

    public ExcludeFilesSettingsConfigurable(Project project) {
        this.project = project;
    }

    private ExcludeFilesSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Monorepo Exclude Files";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new ExcludeFilesSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ExcludeFilesSettingsState settings = ExcludeFilesSettingsState.getInstance(project);
        boolean modified = mySettingsComponent.getExcludeFilesOnBranchChange() != settings.excludeFilesOnBranchChange;
        modified |= mySettingsComponent.getExcludeFilesOnStartUp() != settings.excludeFilesOnStartUp;
        modified |= !mySettingsComponent.getExcludeGlobsForVcs().equals(settings.excludeGlobsForVcs);
        return modified;
    }

    @Override
    public void apply() {
        ExcludeFilesSettingsState settings = ExcludeFilesSettingsState.getInstance(project);
        settings.excludeFilesOnBranchChange = mySettingsComponent.getExcludeFilesOnBranchChange();
        settings.excludeFilesOnStartUp = mySettingsComponent.getExcludeFilesOnStartUp();
        settings.excludeGlobsForVcs = mySettingsComponent.getExcludeGlobsForVcs();
    }

    @Override
    public void reset() {
        ExcludeFilesSettingsState settings = ExcludeFilesSettingsState.getInstance(project);
        mySettingsComponent.setExcludeFilesOnBranchChange(settings.excludeFilesOnBranchChange);
        mySettingsComponent.setExcludeFilesOnStartUp(settings.excludeFilesOnStartUp);
        mySettingsComponent.setExcludeGlobsForVcs(settings.excludeGlobsForVcs);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
