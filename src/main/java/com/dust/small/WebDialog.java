package com.dust.small;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class WebDialog extends JDialog {
    private JPanel contentPane;
    private JLabel LableBtn;
    private JEditorPane editorPane;

    public WebDialog() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);

        setTitle("打包机");
        setBounds(200,100,400,600);

//        LableBtn.setText("<html><a href='http://www.google.com'>google</a></html>");

//
//        JLabel linklabel = new JLabel("<html><a href='http://www.google.com'>google</a></html>");
//        LableBtn.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                try {
//                    Runtime.getRuntime().exec("cmd.exe /c start " + "http://www.google.com");
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        try {
            editorPane.setPage("http://192.168.53.129:8080");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 带滑动条的组件 用于存放显示html的jep组件
//        JScrollPane scrollpane = new JScrollPane(editorPane);


        // 添加超链接点击事件回调函数 并将JEditorPane的页面改为超链接的页面
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if(event.getEventType()== HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        editorPane.setPage(event.getURL());
                    } catch (IOException e) {
                        editorPane.setText("<html>Error! Could not load page</html>");
                    }
                }
            }
        });



//        //上面的Runtime语句可用此句代替Runtime.getRuntime().exec("explorer+ "http://www.google.com");
//

    }
}
