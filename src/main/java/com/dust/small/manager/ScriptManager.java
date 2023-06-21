package com.dust.small.manager;

import com.dust.small.entity.ScriptInfo;
import com.intellij.openapi.project.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理Script Tab下的脚本记录
 */
public class ScriptManager {

    private static final String CONFIG_NAME = "Scripts.xml";

    private static final String NODE_SCRIPT = "Script";
    private static final String NSCRIPT_DEFULT = "Script_Defult";
    private static final String NODE_NAME = "Name";
    private static final String NODE_DESCRIPTION = "Description";
    private static final String NODE_CODE = "Code";

    private List<ScriptInfo> mScriptList = new ArrayList<>();
    //打工人按钮使用
    private ScriptInfo fasterScript=new ScriptInfo();

    private ScriptManager() {
    }

    private static ScriptManager INSTANCE = new ScriptManager();

    public static ScriptManager getInstance() {
        return INSTANCE;
    }

    public void load(Project project) {
        //清除缓存
        mScriptList.clear();
        File configFile = new File(project.getBasePath(), CONFIG_NAME);
        if (!configFile.exists()) return;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(configFile);
            NodeList scriptList = document.getElementsByTagName(NODE_SCRIPT);

             getDefaultConfig(document);
            if (scriptList == null || scriptList.getLength() < 1) return;
            for (int i = 0, j = scriptList.getLength(); i < j; i++) {
                ScriptInfo scriptInfo = new ScriptInfo();
                Node script = scriptList.item(i);
                NodeList childNodes = script.getChildNodes();
                for (int x = 0, y = childNodes.getLength(); x < y; x++) {
                    Node item = childNodes.item(x);
                    String content = item.getTextContent();
                    switch (item.getNodeName()) {
                        case NODE_NAME:
                            scriptInfo.name = content;
                            break;
                        case NODE_DESCRIPTION:
                            scriptInfo.description = content;
                            break;
                        case NODE_CODE:
                            scriptInfo.code = content;
                            break;
                    }
                }
                mScriptList.add(scriptInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDefaultConfig(Document document) {
        NodeList scriptList = document.getElementsByTagName(NSCRIPT_DEFULT);
        if (scriptList == null || scriptList.getLength() < 1) return;
        Node script = scriptList.item(0);
        NodeList childNodes = script.getChildNodes();
        for (int x = 0, y = childNodes.getLength(); x < y; x++) {
            Node item = childNodes.item(x);
            String content = item.getTextContent();
            switch (item.getNodeName()) {
                case NODE_NAME:
                    fasterScript.name = content;
                    break;
                case NODE_DESCRIPTION:
                    fasterScript.description = content;
                    break;
                case NODE_CODE:
                    fasterScript.code = content;
                    break;
            }
        }

    }

    public List<ScriptInfo> get() {
        return mScriptList;
    }

    public boolean hasContent() {
        return mScriptList.size() > 0;
    }


    public ScriptInfo getFaster() {
        return fasterScript;
    }
}
