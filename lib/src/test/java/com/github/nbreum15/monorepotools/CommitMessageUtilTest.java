package com.github.nbreum15.monorepotools;

import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;
import com.github.nbreum15.monorepotools.config.dto.ExpandFolderDTO;
import com.github.nbreum15.monorepotools.config.dto.FolderLevelDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommitMessageUtilTest {

    private static final CommitMessageConfigDTO CONFIG = CommitMessageConfigDTO.builder()
            .folderLevels(List.of(
                    FolderLevelDTO.builder()
                            .folderName("apis")
                            .level(1)
                            .build(),
                    FolderLevelDTO.builder()
                            .folderName("customers")
                            .level(2)
                            .build(),
                    FolderLevelDTO.builder()
                            .folderName("projects/subprojects")
                            .level(2)
                            .build(),
                    FolderLevelDTO.builder()
                            .folderName("projects")
                            .level(1)
                            .build()
            ))
            .expandFolders(List.of(
                    ExpandFolderDTO.builder()
                            .folderParts(List.of("modules", "submodules"))
                            .build(),
                    ExpandFolderDTO.builder()
                            .folderParts(List.of("modules"))
                            .build()
            ))
            .rootFolder("root")
            .build();

    @ParameterizedTest(name = "{0}")
    @MethodSource("commit_message_test_source")
    void commit_message(String description, String newCommitMessage, CommitMessagePrefixBuilder builder, String oldCommitMessage, String branch) {
        assertEquals(newCommitMessage, builder.build(oldCommitMessage, branch).getCommitMessage());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("project_changes_test_source")
    void commit_message(String description, String expected, String actual) {
        assertEquals(expected, actual);
    }

    static Stream<Arguments> project_changes_test_source() {
        var args = new ArrayList<Arguments>();

        args.add(Arguments.of(
                "level 1 folder - apis",
                "[apis]",
                getProjectChanges(List.of("/project/apis/pom.xml"))
        ));

        args.add(Arguments.of(
                "level 1 folder - apis-project",
                "[apis-project]",
                getProjectChanges(List.of("/project/apis/apis-project/pom.xml"))
        ));

        args.add(Arguments.of(
                "level 1 folder - apis-project",
                "[apis-project]",
                getProjectChanges(List.of("/project/apis/apis-project/src/main.java"))
        ));

        args.add(Arguments.of(
                "level 2 folder - customer-api",
                "[customer-api]",
                getProjectChanges(List.of("/project/customers/customer/customer-api/pom.xml"))
        ));

        args.add(Arguments.of(
               "level 2 folder - customer",
                "[customer]",
                getProjectChanges(List.of("/project/customers/customer/pom.xml"))
        ));

        args.add(Arguments.of(
                "level 2 folder - customers",
                "[customers]",
                getProjectChanges(List.of("/project/customers/pom.xml"))
        ));

        args.add(Arguments.of(
                "top-level projects - default in many cases",
                "[other-project]",
                getProjectChanges(List.of("/project/other-project/pom.xml"))
        ));

        args.add(Arguments.of(
                "root folder",
                "[root]",
                getProjectChanges(List.of("/project/pom.xml"))
        ));

        args.add(Arguments.of(
                "expand two levels",
                "[modules/modules1]",
                getProjectChanges(List.of("/project/modules/modules1/pom.xml"))
        ));

        args.add(Arguments.of(
                "expand three levels",
                "[modules/submodules/submodule1]",
                getProjectChanges(List.of("/project/modules/submodules/submodule1/pom.xml"))
        ));

        args.add(Arguments.of(
                "folder levels are ordered",
                "[project1]",
                getProjectChanges(List.of("/project/projects/project1/pom.xml"))
        ));

        args.add(Arguments.of(
                "folder levels are ordered - src",
                "[project1]",
                getProjectChanges(List.of("/project/projects/project1/src/pom.xml"))
        ));

        args.add(Arguments.of(
                "folder levels are ordered",
                "[subproject1]",
                getProjectChanges(List.of("/project/projects/subprojects/subproject1/pom.xml"))
        ));

        return args.stream();
    }

    private static String getProjectChanges(List<String> files) {
        return CommitMessagePrefixBuilder.builder()
                .projectDirectory("/project")
                .changedFiles(files)
                .config(CONFIG)
                .build(null, null)
                .getChangedProjectsString();
    }

    static Stream<Arguments> commit_message_test_source() {
        var args = new ArrayList<Arguments>();

        args.add(Arguments.of(
                "inserts prefix",

                "#1234 [new-project] my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of(
                                "/project/new-project/file.txt"
                        ))
                        .config(CONFIG),
                "my message",
                "1234-feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, inserts new",

                "#1234 [new-project] my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of(
                                "/project/new-project/file.txt"
                        ))
                        .config(CONFIG),
                "[project] my message",
                "1234-feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, insert new",

                "#1234 [new-project] my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of(
                                "/project/new-project/file.txt"
                        ))
                        .config(CONFIG),
                "#4321 [project] my message",
                "1234-feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, inserts new without id",

                "[new-project] my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of(
                                "/project/new-project/file.txt"
                        ))
                        .config(CONFIG),
                "#1234 [project] my message",
                "feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, inserts new without id",

                "[new-project] my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of(
                                "/project/new-project/file.txt"
                        ))
                        .config(CONFIG),
                "[project] my message",
                "feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, keeps message only",

                "my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of())
                        .config(CONFIG),
                "[project] my message",
                "feature"
        ));

        args.add(Arguments.of(
                "erases old prefix, keeps message only",

                "my message",
                CommitMessagePrefixBuilder.builder()
                        .projectDirectory("/project")
                        .changedFiles(List.of())
                        .config(CONFIG),
                "#1234 [project] my message",
                "feature"
        ));

        return args.stream();
    }
}