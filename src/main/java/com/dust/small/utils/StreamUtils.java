package com.dust.small.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class StreamUtils {

    public static String copyStream(@NotNull InputStream inputStream, @NotNull OutputStream outputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage() == null ? "文件拷贝失败~" : e.getMessage();
        } finally {
            close(inputStream);
            close(outputStream);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
