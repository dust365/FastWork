package com.dust.small.utils;

import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;
import org.gradle.util.TextUtil;

import java.io.*;

public class FileUtils {

    public static void copyToFileSystem(String resourceName,
                                        File outputFile,
                                        String[] matchArray,
                                        String[] replaceArray) {
        InputStream inputStream = null;
        FileWriter writer = null;
        try {
            inputStream = FileUtils.class.getResource(resourceName).openStream();
            String content = StreamUtil.readText(inputStream, "utf-8");
            if (matchArray != null) {
                for (int i = 0; i < matchArray.length; i++) {
                    String match = matchArray[i];
                    String replace = replaceArray[i];
                    content = content.replace(match, replace);
                }
            }
            createDirectory(outputFile.getParentFile());
            if (!outputFile.exists() || !outputFile.isFile()) {
                outputFile.createNewFile();
            }
            writer = new FileWriter(outputFile);
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(inputStream);
            StreamUtil.closeStream(writer);
        }
    }

    public static void appendMsgToFile(File file, String content) {
        FileInputStream fileInputStream = null;
        String fileContent = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileContent = StreamUtil.readText(fileInputStream, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(fileInputStream);
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content + "\n");
            if (fileContent != null) {
                fileWriter.write(fileContent);
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(fileWriter);
        }
    }

    public static File getFile(String parent, String child) {
        return new File(parent, child.replace("/", File.separator));
    }

    public static void refreshFile(String filePath) {
        refreshFile(new File(filePath), false);
    }

    public static void refreshFile(File file, boolean async) {
        if (!file.exists()) return;
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        if (virtualFile == null) return;
        VfsUtil.markDirtyAndRefresh(async, file.isDirectory(), file.isDirectory(), virtualFile);
    }

    public static void createDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) return;
        directory.mkdirs();
    }
}
