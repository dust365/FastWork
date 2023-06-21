package com.dust.small.utils;

import com.android.tools.idea.gradle.project.sync.GradleSyncInvoker;
import com.android.tools.idea.gradle.task.AndroidGradleTaskManager;
import com.google.wireless.android.sdk.stats.GradleSyncStats;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskType;
import com.intellij.openapi.project.Project;
import org.jetbrains.plugins.gradle.settings.DistributionType;
import org.jetbrains.plugins.gradle.settings.GradleExecutionSettings;
import org.jetbrains.plugins.gradle.util.GradleConstants;

import java.util.List;
import java.util.Objects;

public class GradleUtils {

    public static void execute(Project project, List<String> taskNameList, List<String> gradleOptions, ExternalSystemTaskNotificationListener listener) {
        GradleExecutionSettings gradleEnv = new GradleExecutionSettings(null, null, DistributionType.DEFAULT_WRAPPED, false);
        gradleEnv.withArguments(gradleOptions);
        new AndroidGradleTaskManager().executeTasks(
                ExternalSystemTaskId.create(GradleConstants.SYSTEM_ID, ExternalSystemTaskType.EXECUTE_TASK, project),
                taskNameList,
                Objects.requireNonNull(project.getBasePath()),
                gradleEnv,
                null,
                listener);
    }

    public static void startSyncProject(Project project) {
        GradleSyncInvoker.getInstance().requestProjectSync(project, new GradleSyncInvoker.Request(GradleSyncStats.Trigger.TRIGGER_USER_REQUEST));
    }
}
