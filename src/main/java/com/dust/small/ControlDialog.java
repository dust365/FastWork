package com.dust.small;

import com.fasterxml.aalto.util.TextUtil;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.Application;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class ControlDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonClear;
//    private JButton buttonCancel;
    private JButton ShellButton;
    private JButton DownButton;
    private JTextField pathTextShow;
    private JButton pathSelectButton;
    private JTextArea textAreaResult;
    private JLabel pathtips;
    private JLabel projectPathTips;
    private JTextField ProjectPathTextShow;
    private JButton ProjectPathSelectButton;
    private JScrollPane scrollView;

    private final  String TRACK_PATH_KEY = "track_project_path_key";
    private final  String MAIN_PROJECT_PATH_KEY = "main_project_path_key";


    public ControlDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonClear);

        setTitle("埋点自动化助手");


//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

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

        setBounds(200,100,600,400);

        //滚动调
//        scrollView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

          initView();

        initClick();

    }

    private void initView() {

        //默认地址
        String defaltPath="/Users/chenhui/AndroidStudioProjects/funbox/funboxdaschema";
        String defaltProjectPath="/Users/chenhui/AndroidStudioProjects/funbox/Funbox/track/script/gen_track_code";
        //test
        pathTextShow.setText(defaltPath);
        ProjectPathTextShow.setText(defaltProjectPath);


        String  trackPath=  PropertiesComponent.getInstance().getValue(TRACK_PATH_KEY);
        System.out.println("------- initView -------"+trackPath);
        if (trackPath!=null&&trackPath.length()>0){
            pathTextShow.setText(trackPath);
        }else {
//            pathTextShow.setText(defaltPath);
        }
        String  projectPath=  PropertiesComponent.getInstance().getValue(MAIN_PROJECT_PATH_KEY);
        System.out.println("------- initView -------"+projectPath);
        if (projectPath!=null&&projectPath.length()>0){
            ProjectPathTextShow.setText(projectPath);
        }else {
//            ProjectPathTextShow.setText(defaltProjectPath);
        }

        if (trackPath==null||projectPath==null){
          textAreaResult.setText("样例如下：\n");
          textAreaResult.append(defaltPath);
          textAreaResult.append("\n");
          textAreaResult.append(defaltProjectPath);
        }

    }

    private void initClick() {



       //清空
        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreaResult.setText("");
            }
        });


        //选择埋点项目路径
        pathSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FlieSelectUtils.showFileOpenDialog(contentPane,pathTextShow,1);
            }
        });
        //选择主工程项目路径
        ProjectPathSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FlieSelectUtils.showFileOpenDialog(contentPane,ProjectPathTextShow,2);
            }
        });



        //拉取
        DownButton .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savaAndCheckPath();
                System.out.println("执行git取 la");
                String currentPath = pathTextShow.getText();
                textAreaResult.setText("开始拉取\n");
                textAreaResult.append("当前埋点库路径："+currentPath+"\n");
//                String[] args = new String[] { "python3", projectPath+"main.py", a, b, c, d };
//                Runtime.getRuntime().exec(args);
                try {
                    Process  p = Runtime.getRuntime().exec("git pull",null,new File(currentPath));
                    printResult(p);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }finally {
                    System.out.println("------- git拉取成功 -------");
                }

            }
        });




        //执行shell脚本
        ShellButton .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savaAndCheckPath();
                System.out.println("执行shell脚本");
                String projectPath = ProjectPathTextShow.getText();
                textAreaResult.setText("执行shell脚本\n");
                textAreaResult.append("当前主工程track路径："+projectPath+"\n");
                try {
                    Process p = Runtime.getRuntime().exec("python3 main.py",null,new File(projectPath));
                    printResult(p);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    System.out.println(exception.getMessage());
                }finally {
                    System.out.println("------- 埋点生成成功 -------");
                }


            }
        });





    }

    private void savaAndCheckPath() {
        String currentPath = pathTextShow.getText();
        if (currentPath!=null&&currentPath.length()>0){
            PropertiesComponent.getInstance().setValue(TRACK_PATH_KEY,currentPath);
        }else {
            ToastUtils.INSTANCE.showToast("路径1不能为空");
        }
        String projectPath = ProjectPathTextShow.getText();
        if (currentPath!=null&&currentPath.length()>0){
            PropertiesComponent.getInstance().setValue(MAIN_PROJECT_PATH_KEY,projectPath);
        }else {
           ToastUtils.INSTANCE.showToast("路径2不能为空");
        }


    }


    private void printResult(Process p)  {

        try {

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {     //按行打印输出内容
            System.out.println(line);
            textAreaResult.append(line+"\n");
        }
        in.close();
        p.waitFor();

    } catch (IOException exception) {
        exception.printStackTrace();
        System.out.println(exception.getMessage());
    } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
    }finally {
        textAreaResult.append("----执行完成--------\n");
        p.destroy();
    }


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
        ControlDialog dialog = new ControlDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }










}
