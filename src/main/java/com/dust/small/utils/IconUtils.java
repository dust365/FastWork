package com.dust.small.utils;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class IconUtils {

    @NotNull
    public static JComponent getMenuIcon(Icon icon, String tip, MouseListener listener) {
        JLabel label = new JLabel(icon);
        label.setToolTipText(tip);
        label.addMouseListener(listener);
        return label;
    }
}
