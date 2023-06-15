package com.github.nbreum15.monorepotools.extensions;

import com.github.nbreum15.monorepotools.actions.CommitMessageAction;
import com.github.nbreum15.monorepotools.settings.autocommitmessage.AutoCommitMessageSettingsState;
import com.intellij.openapi.project.Project;
import com.intellij.vcs.commit.CommitWorkflowUi;
import com.intellij.vcs.commit.DelayedCommitMessageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link com.intellij.openapi.vcs.changes.ui.CommitMessageProvider} may be a suitable alternative if this interface is removed.
 */
public class AutomaticCommitMessageProvider implements DelayedCommitMessageProvider {

    @Override
    public void init(@NotNull Project project, @NotNull CommitWorkflowUi commitWorkflowUi, @Nullable String s) {
        commitWorkflowUi.addInclusionListener(
                () -> {
                    if (AutoCommitMessageSettingsState.getInstance(project).toConfig().automaticCommitMessage()) {
                        CommitMessageAction.setNewCommitMessage(project);
                    }
                },
                () -> { /* do nothing */ });
    }
}
