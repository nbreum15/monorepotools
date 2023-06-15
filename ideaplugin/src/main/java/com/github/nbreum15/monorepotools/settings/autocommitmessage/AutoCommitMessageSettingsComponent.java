package com.github.nbreum15.monorepotools.settings.autocommitmessage;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBEmptyBorder;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;

public class AutoCommitMessageSettingsComponent {

    private final JPanel myMainPanel;

    private final JBCheckBox myAutomaticCommitMessageCheckBox = new JBCheckBox("Automatic commit message");

    public AutoCommitMessageSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(new JBLabel("Auto commit message", UIUtil.ComponentStyle.LARGE).withBorder(new JBEmptyBorder(0, 0, 0, 0)))
                .addComponent(myAutomaticCommitMessageCheckBox, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myAutomaticCommitMessageCheckBox;
    }

    public boolean getAutomaticCommitMessageSelected() {
        return myAutomaticCommitMessageCheckBox.isSelected();
    }

    public void setAutomaticCommitMessageCheckBox(boolean newSelected) {
        myAutomaticCommitMessageCheckBox.setSelected(newSelected);
    }
}
