package com.github.nbreum15.monorepotools.extensions;

import com.github.nbreum15.monorepotools.actions.CommitMessageAction;
import com.github.nbreum15.monorepotools.settings.autocommitmessage.AutoCommitMessageSettingsState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.vcs.changes.ChangesViewWorkflowManager;
import com.intellij.vcs.commit.CommitWorkflowUi;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class AutomaticCommitMessageProvider implements ProjectActivity {

    private final static AtomicBoolean inclusionListenerAdded = new AtomicBoolean(false);

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        project.getMessageBus().connect().subscribe(ChangesViewWorkflowManager.TOPIC, (ChangesViewWorkflowManager.ChangesViewWorkflowListener) () -> {
            if (inclusionListenerAdded.compareAndSet(false, true)) {
                ChangesViewWorkflowManager changesViewWorkflowManager = ChangesViewWorkflowManager.getInstance(project);
                var commitWorkFlowHandler = changesViewWorkflowManager.getCommitWorkflowHandler();

                if (commitWorkFlowHandler == null) {
                    return;
                }

                CommitWorkflowUi ui = commitWorkFlowHandler.getUi();
                ui.addInclusionListener(
                        () -> {
                            if (AutoCommitMessageSettingsState.getInstance(project).toConfig().automaticCommitMessage()) {
                                CommitMessageAction.setNewCommitMessage(project);
                            }
                        },
                        () -> { /* do nothing */ });
            }
        });
        return null;
    }
}
