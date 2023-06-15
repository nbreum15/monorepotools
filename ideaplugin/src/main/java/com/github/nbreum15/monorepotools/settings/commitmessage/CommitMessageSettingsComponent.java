package com.github.nbreum15.monorepotools.settings.commitmessage;

import com.github.nbreum15.monorepotools.settings.commitmessage.dto.ExpandFolderDTO;
import com.github.nbreum15.monorepotools.settings.commitmessage.dto.FolderLevelDTO;
import com.github.nbreum15.monorepotools.settings.ui.GuiUtil;
import com.github.nbreum15.monorepotools.settings.ui.expandfolder.ExpandFolderPanel;
import com.github.nbreum15.monorepotools.settings.ui.folderlevel.FolderLevelPanel;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBEmptyBorder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CommitMessageSettingsComponent {

    private final JPanel myMainPanel;
    private final FolderLevelPanel folderLevelPanel;
    private final ExpandFolderPanel expandFolderPanel;
    private final JBTextField rootFolderText = new JBTextField();

    public CommitMessageSettingsComponent() {
        folderLevelPanel = new FolderLevelPanel("Folder levels");
        expandFolderPanel = new ExpandFolderPanel("Expand folders");

        var panel = new JPanel(new BorderLayout());
        panel.setBorder(GuiUtil.createTitledBorder("Root folder"));
        panel.add(rootFolderText);

        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(new JBLabel("Commit message", UIUtil.ComponentStyle.LARGE).withBorder(new JBEmptyBorder(0, 0, 0, 0)))
                .addComponent(panel)
                .addComponent(folderLevelPanel)
                .addComponent(expandFolderPanel)


                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return rootFolderText;
    }

    @NotNull
    public String getRootFolderText() {
        return rootFolderText.getText();
    }

    public void setRootFolderText(@NotNull String newText) {
        rootFolderText.setText(newText);
    }

    @NotNull
    public List<FolderLevelDTO> getFolderLevels() {
        return folderLevelPanel.getRows();
    }

    public void setFolderLevels(List<FolderLevelDTO> folderLevels) {
        folderLevelPanel.setRows(folderLevels);
    }

    public List<ExpandFolderDTO> getExpandFolders() {
        return expandFolderPanel.getRows();
    }

    public void setExpandFolders(List<ExpandFolderDTO> expandFolders) {
        expandFolderPanel.setRows(expandFolders);
    }

}
