package com.github.nbreum15.monorepotools.listeners;

import com.github.nbreum15.monorepotools.settings.excludefiles.ExcludeFilesSettingsState;
import com.github.nbreum15.monorepotools.settings.excludefiles.dto.ExcludeFilesConfigDTO;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.FilesScanningListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import git4idea.commands.Git;
import git4idea.commands.GitCommand;
import git4idea.commands.GitLineHandler;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ExcludeFilesProjectStartupListener implements ProjectActivity {

    private static final Logger LOGGER = Logger.getInstance(ExcludeFilesProjectStartupListener.class);

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        if (ExcludeFilesSettingsState.getInstance(project).toConfig().excludeFilesOnStartUp()) {
            project.getMessageBus().connect().subscribe(FilesScanningListener.TOPIC, new FilesScanningListener() {
                @Override
                public void filesScanningFinished() {
                    try {
                        excludeFoldersWithFiles(project);
                    } catch (VcsException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        return null;
    }

    public static void excludeFoldersWithFiles(Project project) throws VcsException {
        ExcludeFilesConfigDTO config = ExcludeFilesSettingsState.getInstance(project).toConfig();
        if (config.getExcludeGlobsForVcs().isEmpty()) {
            return;
        }
        var modules = ModuleManager.getInstance(project).getModifiableModel().getModules();

        if (modules.length == 0) {
            return;
        }

        Module module = modules[0];
        ModuleRootManager rootModule = ModuleRootManager.getInstance(module);

        if (rootModule.getContentRoots().length == 0) {
            return;
        }

        VirtualFile fileRoot = rootModule.getContentRoots()[0];
        List<String> files = lsFiles(project, fileRoot, config.getExcludeGlobsForVcs());

        LOGGER.warn("Will try to exclude the parent folders of the following files: %s".formatted(files));

        List<String> directories = files.stream()
                .map(file -> Path.of(file).getParent().toString())
                .toList();
        ModuleRootModificationUtil.updateModel(module, getModifiableRootModelConsumer(directories));
    }

    @SuppressWarnings("UsagesOfObsoleteApi")
    @NotNull
    private static Consumer<ModifiableRootModel> getModifiableRootModelConsumer(List<String> files) {
        return modifiableModel -> {
            if (modifiableModel.getContentEntries().length == 0) {
                return;
            }

            ContentEntry contentEntry = modifiableModel.getContentEntries()[0];
            VirtualFile contentFile = contentEntry.getFile();

            if (contentFile == null) {
                LOGGER.warn("Could not get content file from content entry.");
                return;
            }

            for (String file : files) {
                VirtualFile relativeFile = contentFile.findFileByRelativePath(file);
                if (relativeFile == null) {
                    LOGGER.warn("Could not relativize file %s to project directory %s".formatted(file, contentFile));
                    continue;
                }
                contentEntry.addExcludeFolder(relativeFile);
            }
        };
    }

    @NotNull
    private static List<String> lsFiles(
            @NotNull Project project,
            @NotNull VirtualFile root,
            Set<String> globs) throws VcsException {
        GitLineHandler handler = new GitLineHandler(project, root, GitCommand.LS_FILES);
        handler.setSilent(true);
        handler.endOptions();
        for (var glob : globs) {
            handler.addParameters(glob);
        }
        String output = Git.getInstance().runCommand(handler).getOutputOrThrow();

        if (output.isBlank()) {
            return List.of();
        }

        return Arrays.asList(StringUtil.splitByLines(output));
    }
}
