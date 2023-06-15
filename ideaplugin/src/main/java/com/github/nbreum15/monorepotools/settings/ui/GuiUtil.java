package com.github.nbreum15.monorepotools.settings.ui;

import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.border.IdeaTitledBorder;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class GuiUtil {
    @NotNull
    public static IdeaTitledBorder createTitledBorder(@NotNull final String title) {
        return IdeBorderFactory.createTitledBorder(title, true,
                JBUI.insets(IdeBorderFactory.TITLED_BORDER_TOP_INSET, 0, IdeBorderFactory.TITLED_BORDER_BOTTOM_INSET, IdeBorderFactory.TITLED_BORDER_RIGHT_INSET));
    }

    @NotNull
    public static JBTable createCheckboxTable(@NotNull final TableModel model) {
        final JBTable ret = new JBTable() {
            @Override
            public void setRowHeight(final int rowHeight) {
                // do not allow this ; JTable#initializeLocalVars set a default value which will set JBTable#myRowHeightIsExplicitlySet
            }
        };
        ret.setModel(model);
        ret.setShowGrid(false);
        ret.setIntercellSpacing(new Dimension(0, 0));
        ret.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        ret.setColumnSelectionAllowed(false);
        return ret;
    }
}
