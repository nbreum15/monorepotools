package com.github.nbreum15.monorepotools;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.nbreum15.monorepotools.configwrappers.ProjectWrapperDTO;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "Print commit prefix", mixinStandardHelpOptions = true, version = "0.1.8",
        description = """
                Prints the project names for a list of files to STDOUT.
                
                Example:
                git diff --name-only --cached | xargs ./monorepotools \\
                    -config=file:.idea/MonorepoCommitMessage.xml \\
                    -branch-name=$(git rev-parse --abbrev-ref HEAD)
                    
                    """)
class CommitMessageCLI implements Callable<Integer> {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @Parameters(index = "0", description = "Files to find project names for.", arity = "1..*")
    private String[] files;

    @Option(names = {"-c", "-config"}, description = "XML config file. NOTE: should be prefixed with: 'file:'", required = true)
    private String configFile;

    @Option(names = {"-p", "-project-dir"}, defaultValue = ".", description = "Project directory. This is used to relativize absolute paths if given.")
    private String projectDirectory;

    @Option(names = {"-b", "-branch-name"}, defaultValue = "", description = "Branch name if issue id should be filled out.", required = false)
    private String branchName;

    CommitMessageCLI() {
    }

    @Override
    public Integer call() throws Exception {
        var mapper = new XmlMapper();
        // Read Jetbrains' XML using wrapper objects since their XML output format is weird compared to Jacksons' expectations.
        ProjectWrapperDTO configWrapper = mapper.readValue(new URL(configFile), ProjectWrapperDTO.class);
        var builder = CommitMessagePrefixBuilder.builder()
                .config(configWrapper.getComponent().getConfig().getCommitMessageConfigDTO())
                .changedFiles(Arrays.stream(files).toList())
                .projectDirectory(projectDirectory);
        var result = builder.build("", branchName);
        spec.commandLine().getOut().printf(result.getCommitPrefix());
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new CommitMessageCLI()).execute(args);
        System.exit(exitCode);
    }
}
