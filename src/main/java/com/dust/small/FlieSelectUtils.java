package com.dust.small;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FlieSelectUtils {

    private static final  String TRACK_PATH_KEY = "track_project_path_key";
    private static final  String MAIN_PROJECT_PATH_KEY = "main_project_path_key";


    /*
     * 打开文件
     */
    public static void showFileOpenDialog(Component parent, JTextField pathTextView,int type) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
//        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setCurrentDirectory(new File("/Users/*/AndroidStudioProjects"));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
//        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            String absolutePath = file.getAbsolutePath();
            pathTextView.setText(absolutePath);
            if (type==1){
                PropertiesComponent.getInstance().setValue(TRACK_PATH_KEY,absolutePath);
            }
            if (type==2){
                PropertiesComponent.getInstance().setValue(MAIN_PROJECT_PATH_KEY,absolutePath);
            }

//            pathTextView.append("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
    }


    /*
     * 选择文件保存路径
     */
    public static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }



}
