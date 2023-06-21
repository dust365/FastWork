package com.dust.small.manager;

import com.intellij.openapi.project.Project;
import org.apache.http.util.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectPropertiesManager {

    private static final String GRADLE_PROPERTIES = "gradle.properties";
    private static final String ASSEMBLE_PATH = "assemblePath";
    private static final String DEBUG = "ikCompileDebug";

    private static ProjectPropertiesManager INSTANCE = new ProjectPropertiesManager();

    private Properties mProperties;

    private ProjectPropertiesManager() {
    }

    public static ProjectPropertiesManager getInstance() {
        return INSTANCE;
    }

    public String getProperty(Project project, String key) {
        if (mProperties == null) {
            mProperties = getProperties(project);
        }
        return mProperties == null ? null : mProperties.getProperty(key);
    }

    private Properties getProperties(Project project) {
        FileInputStream inputStream = null;
        try {
            File file = new File(project.getBasePath(), GRADLE_PROPERTIES);
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void reset() {
        if (mProperties != null) {
            mProperties.clear();
            mProperties = null;
        }
    }

    public String getAssemblePath(Project project) {
        return getProperty(project, ASSEMBLE_PATH);
    }

    /**
     * 当前是不是Debug模式
     *
     * @param project
     * @return
     */
    public boolean isDebug(Project project) {
        String isDebug = getProperty(project, DEBUG);
        return !TextUtils.isEmpty(isDebug) && isDebug.equalsIgnoreCase("true");
    }
}
