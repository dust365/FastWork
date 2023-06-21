package com.dust.small.utils;

import com.android.builder.model.AndroidProject;
//import com.android.tools.idea.gradle.project.sync.ng.AndroidModule;
import com.android.tools.idea.model.AndroidModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.annotations.NotNull;
//import org.jetbrains.jps.android.model.impl.JpsAndroidModuleProperties;

public class AndroidModuleUtils {

    public static String getPackageName(Project project) {
        Module appModule = getAppModule(project);
        if (appModule == null) return null;
        return AndroidModel.get(appModule).getApplicationId();
    }

    public static Module getAppModule(Project project) {
        @NotNull Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
            AndroidFacet androidFacet = AndroidFacet.getInstance(module);
            if (androidFacet == null) continue;
            if (androidFacet.getConfiguration().isAppProject()) {
                return module;
            }
        }
        return null;
    }
}
