package icons;

//import com.intellij.openapi.util.IconLoader;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public final class PluginIcons {

    private static Icon loadIcon(String path) {
        return IconLoader.getIcon(path, PluginIcons.class);
    }

    public static final Icon MAIN_ACTION = loadIcon("/images/main-icon.png");
    public static final Icon TOOL_WINDOW = loadIcon("/images/toolWindowMessages.svg");

    public static final Icon ICON_ADD = loadIcon("/images/add.svg");
    public static final Icon ICON_DELETE = loadIcon("/images/delete.svg");
    public static final Icon ICON_DOWNLOAD = loadIcon("/images/download.svg");
    public static final Icon ICON_UPLOAD = loadIcon("/images/upload.svg");
    public static final Icon ICON_MINUS = loadIcon("/images/minus.svg");
    public static final Icon ICON_PERFORM_APK = loadIcon("/images/perform-apk.svg");
    public static final Icon ICON_PUBLISH_ALL = loadIcon("/images/publish-all.svg");
    public static final Icon ICON_REPOSITORY = loadIcon("/images/repository.svg");
    public static final Icon ICON_SELECT = loadIcon("/images/select.svg");
    public static final Icon ICON_CLEAN_COMPONENT = loadIcon("/images/component-clean.svg");
}
