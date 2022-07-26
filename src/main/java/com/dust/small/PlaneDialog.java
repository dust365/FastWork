package com.dust.small;

import javax.swing.*;
import java.awt.event.*;

public class PlaneDialog extends JDialog {
    private JPanel contentPane;

    private JButton AutoTrackButton;
    private JButton PackgeButton;
    private JButton otherButton;

    public PlaneDialog() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("功能区");

        setBounds(200,100,400,200);
//        getRootPane().setDefaultButton(buttonOK);

        AutoTrackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AutoTrackDialog dialog = new  AutoTrackDialog();
                dialog.setVisible(true);
            }
        });
//
        PackgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        PlaneDialog dialog = new PlaneDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
