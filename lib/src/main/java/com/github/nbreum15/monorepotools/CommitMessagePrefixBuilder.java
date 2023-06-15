package com.github.nbreum15.monorepotools;

import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;

import java.nio.file.Path;
import java.util.List;

import static com.github.nbreum15.monorepotools.CommitMessageUtil.getIssueNumberFromBranchName;
import static com.github.nbreum15.monorepotools.CommitMessageUtil.getNewCommitMessage;
import static com.github.nbreum15.monorepotools.CommitMessageUtil.getProjectChanges;

public class CommitMessagePrefixBuilder {
    private CommitMessageConfigDTO config;
    private Path projectDirectory;
    private List<Path> changedFiles;

    private CommitMessagePrefixBuilder() {
    }

    public static CommitMessagePrefixBuilder builder() {
        return new CommitMessagePrefixBuilder();
    }

    public CommitMessagePrefixBuilder config(CommitMessageConfigDTO config) {
        this.config = config;
        return this;
    }

    public CommitMessagePrefixBuilder projectDirectory(String projectDirectory) {
        this.projectDirectory = Path.of(projectDirectory);
        return this;
    }

    public CommitMessagePrefixBuilder changedFiles(List<String> changedFiles) {
        this.changedFiles = changedFiles.stream().map(Path::of).toList();
        return this;
    }

    public CommitMessagePrefixBuilder changedPaths(List<Path> changedFiles) {
        this.changedFiles = changedFiles;
        return this;
    }

    public CommitMessageResult build(String oldCommitMessage, String branchName) {
        if (changedFiles == null) {
            throw new IllegalStateException("changedFiles must be set either with 'changedFiles' or 'changedPaths'.");
        }

        if (config == null) {
            throw new IllegalStateException("config must be set either with 'config'.");
        }

        if (projectDirectory == null) {
            throw new IllegalStateException("projectDirectory must be set either with 'projectDirectory'.");
        }

        List<String> projectsChanged = getProjectChanges(changedFiles, projectDirectory, config);
        String issueString = getIssueNumberFromBranchName(branchName);
        String oldCommitMessageDefault = oldCommitMessage == null ? "" : oldCommitMessage;
        return getNewCommitMessage(oldCommitMessageDefault, projectsChanged, issueString);
    }
}
