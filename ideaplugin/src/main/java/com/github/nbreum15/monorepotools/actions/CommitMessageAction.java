package com.github.nbreum15.monorepotools.actions;

import com.github.nbreum15.monorepotools.CommitMessagePrefixBuilder;
import com.github.nbreum15.monorepotools.settings.commitmessage.CommitMessageSettingsState;
import com.github.nbreum15.monorepotools.settings.commitmessage.dto.CommitMessageConfigDTO;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangesUtil;
import com.intellij.openapi.vcs.changes.ChangesViewWorkflowManager;
import com.intellij.openapi.vcs.changes.ui.ChangesListView;
import com.intellij.vcs.branch.BranchStateProvider;
import com.intellij.vcs.commit.CommitWorkflowUi;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommitMessageAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        setNewCommitMessage(e.getProject());
    }

    /**
     * Sets the new commit message.
     * @param project Project.
     */
    public static void setNewCommitMessage(Project project) {
        if (project == null) {
            return;
        }

        ChangesViewWorkflowManager changesViewWorkflowManager = ChangesViewWorkflowManager.getInstance(project);
        var commitWorkFlowHandler = changesViewWorkflowManager.getCommitWorkflowHandler();

        if (commitWorkFlowHandler == null) {
            return;
        }

        CommitWorkflowUi ui = commitWorkFlowHandler.getUi();
        // all changes made in VCS
        List<FilePath> vcsChanges = getVcsChanges(ui);
        List<Path> vcsChangesPaths = vcsChanges.stream().map(FilePath::getPath).map(Path::of).toList();

        CommitMessageConfigDTO config = CommitMessageSettingsState.getInstance(project).config;
        String oldCommitMsg = commitWorkFlowHandler.getCommitMessage();
        String branchName = getBranchName(vcsChanges, project);

        var builder = CommitMessagePrefixBuilder.builder()
                .config(ConfigMapper.INSTANCE.map(config))
                .changedPaths(vcsChangesPaths)
                .projectDirectory(project.getBasePath());

        String newCommitMsg = builder.build(oldCommitMsg, branchName).getCommitMessage();

        if (!oldCommitMsg.equals(newCommitMsg)) {
            commitWorkFlowHandler.setCommitMessage(newCommitMsg);
        }
    }

    /**
     * Gets branch name from the {@link BranchStateProvider} extension point list.
     * Not pretty, but works overall. Not sure of the behaviour if multiple VCSs are added to IDEA!
     * @param filePaths Paths to find branch name for.
     * @param project Project.
     * @return Branch name.
     */
    @Nullable
    protected static String getBranchName(List<FilePath> filePaths, Project project) {
        String branchName = null;
        for (FilePath change : filePaths) {
            if (branchName == null) {
                var result = BranchStateProvider.EP_NAME.getExtensionList(project).stream()
                        .map(provider -> provider.getCurrentBranch(change))
                        .filter(Objects::nonNull)
                        .findFirst();
                if (result.isPresent()) {
                    branchName = result.get().getBranchName();
                }
            }
        }
        return branchName;
    }

    /**
     * Get all VCS changes. This includes BOTH versioned and unversioned changes.
     * @return File paths for versioned and unversioned changes.
     */
    protected static List<FilePath> getVcsChanges(CommitWorkflowUi commitWorkflowUi) {
        var includedUnversionedFiles = commitWorkflowUi.getIncludedUnversionedFiles();
        var includedChanges = commitWorkflowUi.getIncludedChanges();

        List<FilePath> vcsChanges = new ArrayList<>();
        vcsChanges.addAll(includedUnversionedFiles);
        vcsChanges.addAll(includedChanges.stream()
                .map(ChangesUtil::getFilePath)
                .toList());
        return vcsChanges;
    }

    /**
     * A hacky way to get the unversioned and versioned changes -- Was used before using the commit UI directly, but may prove useful if the UI approach fails later on.
     */
    protected List<FilePath> getVcsChangesUnused(ChangesListView changesListView) {
        var includedChanges = changesListView.getIncludedSet();
        List<FilePath> vcsChanges = new ArrayList<>();
        for (Object includedChange : includedChanges) {
            // Changes in "Unversioned Files" are not changes in IDEA -- IDEA adds local files to the "Changes" change list right before the files are committed.
            if (includedChange instanceof FilePath p) {
                vcsChanges.add(p);
            } else if (includedChange instanceof Change c) { // Files in "Changes" change list, or other custom change list.
                vcsChanges.add(ChangesUtil.getFilePath(c));
            }
        }
        return vcsChanges;
    }
}
