package com.github.nbreum15.monorepotools;

import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;
import com.github.nbreum15.monorepotools.config.dto.ExpandFolderDTO;
import com.github.nbreum15.monorepotools.config.dto.FolderLevelDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommitMessageUtilTest {
    @Test
    void testConvertToProjectName() {
        CommitMessageConfigDTO dto = CommitMessageConfigDTO.builder()
                .folderLevels(List.of(
                        FolderLevelDTO.builder()
                                .folderName("apis")
                                .level(1)
                                .build(),
                        FolderLevelDTO.builder()
                                .folderName("customers")
                                .level(2)
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

        // level 1 folders tests
        assertEquals("apis", CommitMessageUtil.convertToProjectName(List.of("apis", "pom.xml"), dto));
        assertEquals("apis-project", CommitMessageUtil.convertToProjectName(List.of("apis", "apis-project", "pom.xml"), dto));
        assertEquals("apis-project", CommitMessageUtil.convertToProjectName(List.of("apis", "apis-project", "src", "main.java"), dto));

        // level 2 folders tests
        assertEquals("customer-api", CommitMessageUtil.convertToProjectName(List.of("customers", "customer", "customer-api", "pom.xml"), dto));
        assertEquals("customer", CommitMessageUtil.convertToProjectName(List.of("customers", "customer", "pom.xml"), dto));
        assertEquals("customers", CommitMessageUtil.convertToProjectName(List.of("customers", "pom.xml"), dto));

        // top-level projects - default in many cases
        assertEquals("other-project", CommitMessageUtil.convertToProjectName(List.of("other-project", "pom.xml"), dto));

        // root folder tests
        assertEquals("root", CommitMessageUtil.convertToProjectName(List.of("pom.xml"), dto));

        // expand two levels tests
        assertEquals("modules/modules1", CommitMessageUtil.convertToProjectName(List.of("modules", "modules1", "pom.xml"), dto));
        // expand three levels
        assertEquals("modules/submodules/submodule1", CommitMessageUtil.convertToProjectName(List.of("modules", "submodules", "submodule1", "pom.xml"), dto));
    }

    @Test
    public void testCommitMessage() {
        assertEquals("#1234 [new-project] my message", CommitMessageUtil.getNewCommitMessage("my message", List.of("new-project"), "1234").getCommitMessage());
        assertEquals("#1234 [new-project] my message", CommitMessageUtil.getNewCommitMessage("[project] my message", List.of("new-project"), "1234").getCommitMessage());
        assertEquals("#1234 [new-project] my message", CommitMessageUtil.getNewCommitMessage("#4321 [project] my message", List.of("new-project"), "1234").getCommitMessage());
        assertEquals("[new-project] my message", CommitMessageUtil.getNewCommitMessage("#1234 [project] my message", List.of("new-project"), null).getCommitMessage());
        assertEquals("[new-project] my message", CommitMessageUtil.getNewCommitMessage("[project] my message", List.of("new-project"), null).getCommitMessage());
        assertEquals("my message", CommitMessageUtil.getNewCommitMessage("[project] my message", List.of(), null).getCommitMessage());
        assertEquals("my message", CommitMessageUtil.getNewCommitMessage("#1234 [project] my message", List.of(), "1234").getCommitMessage());
    }
}