package com.dust.small.manager;

import com.dust.small.ToastUtils;
import com.dust.small.callback.OnResultCallBack;
import com.dust.small.entity.ComponentInfo;
import com.dust.small.factory.ComponentFactory;
import com.dust.small.utils.CollectionUtils;
import com.dust.small.utils.FileUtils;
import com.dust.small.utils.GradleUtils;
import com.dust.small.utils.IOUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
//import com.jgoodies.forms.factories.ComponentFactory;

import org.apache.http.util.TextUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSyncManager {

    public static void syncConfigAndSettingFile(Project project) {
        syncConfigFile(project, new OnResultCallBack() {
            @Override
            public void onFail(String error) {
                ToastUtils.INSTANCE.make(project, MessageType.ERROR, error);
            }

            @Override
            public void onSuccess() {
                //同步设置文件
                syncSettingsFile(project, new OnResultCallBack() {
                    @Override
                    public void onFail(String error) {
                        ToastUtils.INSTANCE.make(project, MessageType.ERROR, error);
                    }

                    @Override
                    public void onSuccess() {
                        GradleUtils.startSyncProject(project);
                    }
                });
            }
        });
    }

    public static void syncConfigFile(Project project, OnResultCallBack back) {
        ApplicationManager.getApplication().runWriteAction(() ->
                modifyConfigFile(back));
    }

    public static void syncSettingsFile(Project project, OnResultCallBack back) {
        ApplicationManager.getApplication().runWriteAction(() ->
                modifySettingFile(project, back));
    }

    //修改配置文件
    private static void modifyConfigFile(OnResultCallBack callBack) {
        final ArrayList<ComponentInfo> totalData = ComponentManager.getInstance().getTotalData();
        final String configFilePath = ComponentManager.getInstance().getConfigFilePath();
        ComponentFactory componentFactory = ComponentFactory.create(new File(configFilePath));
        boolean saveSuccess = componentFactory != null && componentFactory.dump(totalData);
        if (saveSuccess) {
            FileUtils.refreshFile(configFilePath);
            if (callBack != null) {
                callBack.onSuccess();
            }
        } else {
            if (callBack != null) {
                callBack.onFail("同步配置文件失败！");
            }
        }
    }

    //修改设置文件
    private static void modifySettingFile(Project project, OnResultCallBack callBack) {
        ArrayList<ComponentInfo> data = ComponentManager.getInstance().getTotalData();
        List<String> binaryComponent = new ArrayList<>();
        List<String> srcComponent = new ArrayList<>();
        for (ComponentInfo info : data) {
            String name = info.name;
            if (!TextUtils.isEmpty(name)) {
                switch (info.mode) {
                    case ComponentInfo.MODE_BINARY:
                        binaryComponent.add(name);
                        break;
                    case ComponentInfo.MODE_SRC:
                        srcComponent.add(name);
                        break;
                }
            }
        }
        String settingsFilePath = project.getBasePath() + File.separator + "settings.gradle";
        File settingsFile = new File(settingsFilePath);
        if (!settingsFile.exists()) {
            if (callBack != null) {
                callBack.onFail("settings.gradle文件未能识别！");
            }
            return;
        }
        List<String> content = getContent(settingsFile);
        if (CollectionUtils.isEmpty(content)) {
            if (callBack != null) {
                callBack.onFail("读取settings.gradle文件内容为空！");
            }
            return;
        }
        List<String> analysisContent = analysisSettingFileContent(content);
        boolean success = updateContentWithDependency(settingsFile, analysisContent, binaryComponent, srcComponent);
        if (success) {
            FileUtils.refreshFile(settingsFilePath);
            if (callBack != null) {
                callBack.onSuccess();
            }
        } else {
            if (callBack != null) {
                callBack.onFail("更新settings.gradle文件失败！");
            }
        }
    }

    //用来分割include语句
    private static List<String> analysisSettingFileContent(List<String> content) {
        ArrayList<String> analysisLine = new ArrayList<>();
        for (String line : content) {
            if (TextUtils.isEmpty(line)) {
                analysisLine.add(line);
                continue;
            }
            if (line.trim().startsWith("include")) {
                String[] modules = line.split(",");
                for (int i = 0; i < modules.length; i++) {
                    String module = modules[i].trim();
                    if (i != 0) {
                        module = "include " + module;
                    }
                    analysisLine.add(module);
                }
            } else {
                analysisLine.add(line);
            }
        }
        return analysisLine;
    }

    private static List<String> getContent(File settingsFile) {
        List<String> content = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(settingsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(reader);
        }
        return content;
    }

    private static boolean updateContentWithDependency(File settingsFile, List<String> content,
                                                       List<String> binaryComponent, List<String> srcComponent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(settingsFile));
            for (String line : content) {
                if (!TextUtils.isEmpty(line)) {
                    for (String component : binaryComponent) {
                        boolean contains = line.contains(component);
                        if (contains) {
                            if (!line.startsWith("//")) {
                                line = "//" + line;
                            }
                            break;
                        }
                    }
                    for (String component : srcComponent) {
                        boolean contains = line.contains(component);
                        if (contains) {
                            if (line.startsWith("//")) {
                                line = removeNode(line);
                            }
                            break;
                        }
                    }
                }
                writer.write(line + "\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(writer);
        }
    }

    private static String removeNode(String line) {
        int start = -1;
        for (int i = 0, j = line.length(); i < j; i++) {
            char tag = line.charAt(i);
            if (tag != '/') {
                start = i;
                break;
            }
        }
        if (start != -1) return line.substring(start);
        return line;
    }
}
