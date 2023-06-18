package com.github.nbreum15.monorepotools;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class CommitMessageResult {
    /**
     * Issue id if one was found in the branch. Null if none was found.
     */
    private String issueId;
    /**
     * The commit prefix part of the new commit message. This is useful if you only want to output the prefix, e.g. as in the case of a CLI.
     */
    private String commitPrefix;
    /**
     * The old commit message but stripped of all its prefixes. This is the old commit message before it's concatenated with the commit prefix.
     */
    private String oldCommitMessageWithoutPrefix;
    /**
     * Changed projects string, e.g. '[project1, project2]'.
     */
    private String changedProjectsString;
    /**
     * New commit message. This string is meant for presentation purposes and will be used by the IntelliJ plugin.
     */
    private String commitMessage;
}
