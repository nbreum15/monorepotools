package com.github.nbreum15.monorepotools;

import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;
import com.github.nbreum15.monorepotools.config.dto.ExpandFolderDTO;
import com.github.nbreum15.monorepotools.config.dto.FolderLevelDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

class CommitMessageUtil {
    /**
     * Converts a file path to a project name.
     * @return Parts
     */
    static String convertToProjectName(List<String> parts, CommitMessageConfigDTO dto) {
        // rare case, shouldn't ever happen
        if (parts.isEmpty()) {
            return null;
        }

        // simple case - the parent of the file is the project root.
        if (parts.size() == 1 && dto.getRootFolder() != null) {
            return dto.getRootFolder();
        }

        for (FolderLevelDTO folder : dto.getFolderLevels()) {
            // case 1: a change may be made as a direct child regardless of level.
            // E.g. For a level 2 folder 'customers', and a change 'customers/file.txt', the change will be in 'customers'
            if (folder.getFolderName().contains(parts.get(0)) && parts.size() == 2) {
                return parts.get(0);
            }
            // case 2: a change may still not be at the level the folder specifies.
            // E.g. For a level 2 folder 'customers', and a change 'customers/customer/file.txt' the change will be in 'customer'
            for (int i = folder.getLevel() + 1; i > 1; i--) {
                if (folder.getFolderName().contains(parts.get(0)) && parts.size() > i) {
                    return (parts.subList(i - 1, parts.size())).get(0);
                }
            }
        }

        for (ExpandFolderDTO expandFolder : dto.getExpandFolders()) {
            boolean matchesAll = parts.size() >= expandFolder.getFolderParts().size()
                    && expandFolder.getFolderParts().equals(parts.subList(0, expandFolder.getFolderParts().size()));
            if (matchesAll) {
                return List.of(String.join("/", parts.subList(0, expandFolder.getFolderParts().size() + 1))).get(0);
            }
        }

        return parts.get(0);
    }

    /**
     * Gets all project changes.
     * @param vcsChanges Vcs Changes.
     * @param projectPath Project path.
     * @return Project names for all vcs changes.
     */
    static List<String> getProjectChanges(List<Path> vcsChanges, Path projectPath, CommitMessageConfigDTO config) {
        return vcsChanges.stream()
                .map(path -> {
                    Path relative = projectPath.relativize(path);
                    List<String> pathParts = StreamSupport
                            .stream(relative.spliterator(), false)
                            .map(Path::toString)
                            .toList();
                    return convertToProjectName(pathParts, config);
                })
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get the new commit message.
     * @param oldCommitMessage Old commit message. This is appended to the new commit message.
     * @return New commit message with issue id (if available) and changed projects.
     */
    static CommitMessageResult getNewCommitMessage(String oldCommitMessage, List<String> projectsChanged, String issueString) {
        // remove any left-over commit prefixes - two cases to handle:
        // 1. [project] msg
        // 2. #1234 [project] msg
        String oldCommitMsgWithoutPrefix = oldCommitMessage.replaceFirst("^\\[.*?] *", "");
        if (oldCommitMsgWithoutPrefix.equals(oldCommitMessage)) { // first replace failed, try next one
            oldCommitMsgWithoutPrefix = oldCommitMsgWithoutPrefix.replaceFirst("^#\\d* \\[.*?] *", "");
        }

        String changedProjectsString = "[" + String.join(", ", projectsChanged) + "]";
        String commitPrefix = ""; // Default if no prefix makes sense. If no projects are changed, we won't insert a prefix.
        // we are not in a branch that starts with an issue number.
        if (issueString == null) {
            if (!projectsChanged.isEmpty()) {
                commitPrefix = changedProjectsString;
            }
        } else { // we are on a branch that starts with an issue number.
            if (!projectsChanged.isEmpty()) {
                commitPrefix = "#%s %s".formatted(issueString, changedProjectsString);
            }
        }
        return CommitMessageResult.builder()
                .oldCommitMessageWithoutPrefix(oldCommitMsgWithoutPrefix)
                .commitPrefix(commitPrefix)
                .issueId(issueString)
                .changedProjectsString(changedProjectsString)
                .commitMessage(commitPrefix == null || commitPrefix.isEmpty()
                        ? oldCommitMsgWithoutPrefix
                        : "%s %s".formatted(commitPrefix, oldCommitMsgWithoutPrefix))
                .build();
    }

    /**
     * Gets the issue number from the branch name.
     * Assumes the issue number is at the beginning of the branch name, e.g.: 1234-my-branch-name
     * @param branchName Branch name.
     * @return Issue number.
     */
    static String getIssueNumberFromBranchName(String branchName) {
        String issueString = null;
        if (branchName != null) {
            var pattern = Pattern.compile("(\\d*)-.*");
            var matcher = pattern.matcher(branchName);
            if (matcher.matches()) {
                String issueNumber = matcher.group(1);
                if (issueNumber != null) {
                    issueString = issueNumber;
                }
            }
        }
        return issueString;
    }
}
