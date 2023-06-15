package com.github.nbreum15.monorepotools.settings.ui.expandfolder;

import com.github.nbreum15.monorepotools.settings.commitmessage.dto.ExpandFolderDTO;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class ExpandFolderModel extends AbstractTableModel {
    private static final int FOLDER_COLUMN = 0;
    @Getter
    @Setter
    @NotNull
    private List<ExpandFolderDTO> rows;

    ExpandFolderModel(@NotNull final List<ExpandFolderDTO> rows) {
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case FOLDER_COLUMN -> "Folder";
            default -> throw new IllegalArgumentException("Column " + column);
        };
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return switch (columnIndex) {
            case FOLDER_COLUMN -> String.class;
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        };
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return switch (columnIndex) {
            case FOLDER_COLUMN -> String.join("/", rows.get(rowIndex).getFolderParts());
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        };
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case FOLDER_COLUMN -> rows.get(rowIndex)
                    .setFolderParts(Arrays.stream(((String) aValue).split("/"))
                            .toList());
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        }
    }
}
