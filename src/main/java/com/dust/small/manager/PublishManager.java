package com.dust.small.manager;

import com.dust.small.entity.ComponentInfo;
import com.dust.small.service.IKPersistentStateService;
import com.dust.small.utils.ToastUtil;
import com.dust.small.utils.VersionComparator;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.TreeSet;
import java.util.regex.Matcher;

public class PublishManager {

    public static void requestVersion(Project project, @NotNull ComponentInfo componentInfo, RequestVersionListener listener) {
        if (ComponentInfo.DEFAULT_VERSION.equals(componentInfo.version)) {
            listener.onSuccess("0.0.1");
            return;
        }
        if (ComponentInfo.TYPE_LOCAL.equals(componentInfo.type)) {
            loadComponentLastVersion(project, componentInfo, listener);
        } else {
            reqComponentLastVersion(project, componentInfo, listener);
        }
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private static void loadComponentLastVersion(Project project, ComponentInfo componentInfo, RequestVersionListener listener) {
        String userHome = System.getProperty("user.home");
        String group = ProjectPropertiesManager.getInstance().getProperty(project, "maven_component_groupId")
                .replaceAll("\\.", Matcher.quoteReplacement(File.separator));
        String localMavenMetadata = new StringBuilder(userHome)
                .append(File.separator)
                .append(".m2")
                .append(File.separator)
                .append("repository")
                .append(File.separator)
                .append(group)
                .append(File.separator)
                .append(componentInfo.name)
                .append(File.separator)
                .append("maven-metadata-local.xml")
                .toString();
        final File localMavenMetaFile = new File(localMavenMetadata);
        if (!localMavenMetaFile.exists()) {
            ToastUtil.make(project, MessageType.WARNING, "本地Maven配置文件不存在，自动切换为以配置版本号发布");
            listener.onSuccess(componentInfo.version);
            return;
        }
        WriteCommandAction.runWriteCommandAction(project, () -> {
            StringWriter writer = null;
            FileReader reader = null;
            try {
                writer = new StringWriter();
                reader = new FileReader(localMavenMetaFile);
                char[] buff = new char[1024];
                int len = -1;
                while ((len = reader.read(buff)) != -1) {
                    writer.write(buff, 0, len);
                }
                writer.flush();
                String fileContent = writer.toString();
                parseVersionXml(fileContent, listener);
            } catch (Exception e) {
                listener.onFail("本地Maven配置文件读取失败");
                return;
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private static void reqComponentLastVersion(Project project, ComponentInfo componentInfo, RequestVersionListener listener) {
        ProjectPropertiesManager propertiesManager = ProjectPropertiesManager.getInstance();
        String host = propertiesManager.getProperty(project, "artifactory_contextUrl");
        String repository = propertiesManager.getProperty(project, ComponentInfo.TYPE_SNAPSHOT.equals(componentInfo.type) ? "snapshot_repo" : "release_repo");
        String group = propertiesManager.getProperty(project, "maven_component_groupId").replaceAll("\\.", "/");
        String url = new StringBuilder(host)
                .append("/")
                .append(repository)
                .append("/")
                .append(group)
                .append("/")
                .append(componentInfo.name)
                .append("/")
                .append("maven-metadata.xml")
                .toString();
        NetworkManager.getInstance().get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail("网络请求失败，请重新尝试或者手动上传");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 404) {
                    ToastUtil.make(project, MessageType.WARNING, "拉取仓库配置文件失败，自动切换为以配置版本号发布");
                    listener.onSuccess(componentInfo.version);
                    return;
                } else if (response.code() != 200) {
                    listener.onFail("拉取仓库配置文件失败，Error Code = " + response.code());
                    return;
                }
                String content = response.body().string();
                if (TextUtils.isEmpty(content)) {
                    listener.onFail("拉取版本配置文件失败，内容为空");
                    return;
                }
                parseVersionXml(content, listener);
            }
        });
    }

    private static void parseVersionXml(String content, RequestVersionListener listener) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(content)));
            NodeList versions = document.getElementsByTagName("version");
            TreeSet<String> versionSet = new TreeSet<String>(new VersionComparator());
            for (int i = 0, j = versions.getLength(); i < j; i++) {
                Node node = versions.item(i);
                versionSet.add(node.getTextContent());
            }
            String lastVersion = versionSet.first();
            int lastIndexOf = lastVersion.lastIndexOf("-SNAPSHOT");
            if (lastIndexOf != -1) {
                lastVersion = lastVersion.substring(0, lastIndexOf);
            }
            String[] versionSplit = lastVersion.split("\\.");
            int versionCount = versionSplit.length;
            int totalNum = 1;
            int maxVersionValue = Integer.parseInt(IKPersistentStateService.getInstance().getValue(IKPersistentStateService.VALUE_MAX_VERSION));
            for (int i = 0; i < versionCount; i++) {
                int value = Integer.parseInt(versionSplit[versionSplit.length - 1 - i]);
                totalNum += value * Math.pow(maxVersionValue, i);
            }
            StringBuilder newVersion = new StringBuilder();
            for (int i = versionCount - 1; i >= 0; i--) {
                int base = (int) (Math.pow(maxVersionValue, i));
                newVersion.append(totalNum / base);
                totalNum %= base;
                if (i != 0) {
                    newVersion.append(".");
                }
            }
            listener.onSuccess(newVersion.toString());
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFail("解析版本配置文件失败！");
        }
    }

    public interface RequestVersionListener {

        void onFail(String error);

        void onSuccess(String version);
    }
}
