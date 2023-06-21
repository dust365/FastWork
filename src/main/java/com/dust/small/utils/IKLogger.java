package com.dust.small.utils;

import com.intellij.openapi.diagnostic.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class IKLogger {

    public static void debug(String info) {
        Logger.getInstance(IKLogger.class).debug(info);
    }

    public static void error(String info) {
        Logger.getInstance(IKLogger.class).error(info);
    }

    public static String getStackTraceString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }
}
