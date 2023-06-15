/*
 * Copyright 2020 SpotBugs plugin contributors
 *
 * This file is part of IntelliJ SpotBugs plugin.
 *
 * IntelliJ SpotBugs plugin is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * IntelliJ SpotBugs plugin is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with IntelliJ SpotBugs plugin.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.nbreum15.monorepotools.settings.ui.expandfolder;

import com.github.nbreum15.monorepotools.settings.commitmessage.dto.ExpandFolderDTO;
import com.github.nbreum15.monorepotools.settings.ui.GuiUtil;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ExpandFolderPanel extends JPanel {

    private final JBTable table;

    public ExpandFolderPanel(@NotNull final String title) {
        super(new BorderLayout());
        setBorder(GuiUtil.createTitledBorder(title));
        var model = new ExpandFolderModel(new ArrayList<>());
        table = GuiUtil.createCheckboxTable(model);

        final JPanel tablePane = ToolbarDecorator.createDecorator(table)
                .setAddAction(anActionButton -> doAdd())
                .setRemoveAction(anActionButton -> doRemove())
                .setMoveUpAction(anActionButton -> moveUp())
                .setMoveDownAction(anActionButton -> moveDown())
                .setAsUsualTopToolbar().createPanel();

        add(tablePane);
    }

    public List<ExpandFolderDTO> getRows() {
        return getModel().getRows();
    }

    public void setRows(List<ExpandFolderDTO> rows) {
        getRows().clear();
        getRows().addAll(rows);
        getModel().fireTableDataChanged();
    }

    @NotNull
    private ExpandFolderModel getModel() {
        return (ExpandFolderModel) table.getModel();
    }

    private void doAdd() {
        getModel().getRows().add(ExpandFolderDTO.builder().build());
        getModel().fireTableDataChanged();
    }

    private void moveUp() {
        int selected = table.getSelectedRow();
        if (selected < 1) {
            return;
        }
        Collections.swap(getRows(), selected, selected - 1);
        getModel().fireTableRowsUpdated(selected - 1, selected);
        table.setRowSelectionInterval(selected - 1, selected - 1);
    }

    private void moveDown() {
        int selected = table.getSelectedRow();
        if (selected >= getRows().size() - 1) {
            return;
        }
        Collections.swap(getRows(), selected, selected + 1);
        getModel().fireTableRowsUpdated(selected, selected + 1);
        table.setRowSelectionInterval(selected + 1, selected + 1);
    }

    private void doRemove() {
        final int[] index = table.getSelectedRows();
        if (index != null && index.length > 0) {
            final Set<ExpandFolderDTO> toRemove = new HashSet<>();
            for (final int idx : index) {
                toRemove.add(getModel().getRows().get(idx));
            }
            getModel().getRows().removeAll(toRemove);
            getModel().fireTableDataChanged();
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        table.setEnabled(enabled);
    }

}
