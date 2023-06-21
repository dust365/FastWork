package com.dust.small.utils;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ProjectUtils {

    public static String getPluginVersion() {
        IdeaPluginDescriptor pluginDescriptor = PluginManager.getPlugin(PluginId.getId("com.meelive.plugins.compile"));
        if (pluginDescriptor != null) {
            return pluginDescriptor.getVersion();
        }
        return "0.0.0";
    }

    public static int compareVersion(@NotNull String first, @NotNull String second) {
        String[] firstSplit = first.split("\\.");
        String[] secondSplit = second.split("\\.");
        for (int i = 0, j = Math.min(secondSplit.length, firstSplit.length); i < j; i++) {
            int firstNum = Integer.parseInt(firstSplit[i]);
            int secondNum = Integer.parseInt(secondSplit[i]);
            if (secondNum == firstNum) continue;
            return secondNum - firstNum;
        }
        return second.length() - first.length();
    }

    public static Window getIDEAWindow(Project project) {
        WindowManager windowManager = WindowManager.getInstance();
        return windowManager.suggestParentWindow(project);
    }

    public static void postDelayTask(Runnable runnable, long time) {
        Application application = ApplicationManager.getApplication();
        application.executeOnPooledThread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            application.invokeLater(runnable);
        });
    }
}
