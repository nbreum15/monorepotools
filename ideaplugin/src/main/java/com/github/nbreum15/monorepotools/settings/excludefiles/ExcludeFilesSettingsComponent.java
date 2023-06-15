package com.github.nbreum15.monorepotools.settings.excludefiles;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBEmptyBorder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ExcludeFilesSettingsComponent {
    private final JPanel myMainPanel;

    private final JBCheckBox myExcludeFilesOnBranchChange = new JBCheckBox("Exclude files on branch change (experimental) ");
    private final JBCheckBox myExcludeFilesOnStartUp = new JBCheckBox("Exclude files on IDE start up (requires restart) ");
    private final JBTextField myExcludeGlobsForVcs = new JBTextField();

    public ExcludeFilesSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(new JBLabel("Exclude files", UIUtil.ComponentStyle.LARGE).withBorder(new JBEmptyBorder(0, 0, 0, 0)))
                .addComponent(myExcludeFilesOnBranchChange, 1)
                .addComponent(myExcludeFilesOnStartUp, 1)
                .addLabeledComponent(new JBLabel("Exclude globs for vcs (csv): "), myExcludeGlobsForVcs, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myExcludeFilesOnBranchChange;
    }

    public boolean getExcludeFilesOnBranchChange() {
        return myExcludeFilesOnBranchChange.isSelected();
    }

    public void setExcludeFilesOnBranchChange(boolean newStatus) {
        myExcludeFilesOnBranchChange.setSelected(newStatus);
    }

    public boolean getExcludeFilesOnStartUp() {
        return myExcludeFilesOnStartUp.isSelected();
    }

    public void setExcludeFilesOnStartUp(boolean newStatus) {
        myExcludeFilesOnStartUp.setSelected(newStatus);
    }

    @NotNull
    public String getExcludeGlobsForVcs() {
        return myExcludeGlobsForVcs.getText();
    }

    public void setExcludeGlobsForVcs(@NotNull String newText) {
        myExcludeGlobsForVcs.setText(newText);
    }
}
