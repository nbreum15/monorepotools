package com.github.nbreum15.monorepotools;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommitMessageCLITest {

    @Test
    void testMain() {
        CommitMessageCLI cli = new CommitMessageCLI();
        CommandLine commandLine = new CommandLine(cli);
        StringWriter sw = new StringWriter();
        commandLine.setOut(new PrintWriter(sw));
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        var url = classloader.getResource("MonorepoCommitMessage.xml");
        int exitCode = commandLine.execute("-config=%s".formatted(url), "-branch-name=1234-my-new-feature", "file.txt", "apis/api-1/file.txt", "modules/submodules/submodule1/file.txt");
        assertEquals(0, exitCode);
        assertEquals("#1234 [api-1, modules/submodules/submodule1, root-new]", sw.toString());
    }

    @Test
    void version() {
        CommitMessageCLI cli = new CommitMessageCLI();
        CommandLine commandLine = new CommandLine(cli);
        StringWriter sw = new StringWriter();
        commandLine.setOut(new PrintWriter(sw));
        int exitCode = commandLine.execute("--version");
        assertEquals(0, exitCode);
        assertEquals(System.getProperty("cli.version") + System.lineSeparator(), sw.toString());
    }
}