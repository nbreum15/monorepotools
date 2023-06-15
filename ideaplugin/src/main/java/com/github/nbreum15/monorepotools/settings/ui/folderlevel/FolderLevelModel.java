package com.github.nbreum15.monorepotools.settings.ui.folderlevel;

import com.github.nbreum15.monorepotools.settings.commitmessage.dto.FolderLevelDTO;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FolderLevelModel extends AbstractTableModel {
    private static final int FOLDER_COLUMN = 0;
    private static final int LEVEL_COLUMN = 1;
    @Getter
    @Setter
    @NotNull
    private List<FolderLevelDTO> rows;

    FolderLevelModel(@NotNull final List<FolderLevelDTO> rows) {
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case LEVEL_COLUMN -> "Level";
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
            case LEVEL_COLUMN -> Integer.class;
            case FOLDER_COLUMN -> String.class;
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        };
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return switch (columnIndex) {
            case LEVEL_COLUMN -> rows.get(rowIndex).getLevel();
            case FOLDER_COLUMN -> rows.get(rowIndex).getFolderName();
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        };
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case LEVEL_COLUMN -> rows.get(rowIndex).setLevel((Integer) aValue);
            case FOLDER_COLUMN -> rows.get(rowIndex).setFolderName((String) aValue);
            default -> throw new IllegalArgumentException("Column " + columnIndex);
        }
    }
}
