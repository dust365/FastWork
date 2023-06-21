package com.dust.small.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件压缩工具类
 */
public class ZipUtil {

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(File zipFile, File destDir) throws IOException {
        try {
            if (!zipFile.exists()) {
                throw new IOException("需解压文件不存在.");
            }
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                String outPath = destDir.getAbsolutePath() + File.separator + zipEntryName;
                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                InputStream in = zip.getInputStream(entry);
                // 输出文件路径信息
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                StreamUtils.close(in);
                StreamUtils.close(out);
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
