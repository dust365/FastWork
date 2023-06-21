package com.dust.small.factory;

import com.dust.small.entity.ComponentInfo;
import org.apache.http.util.TextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;

public class XmlComponentFactory extends ComponentFactory {

    private XmlComponentFactory() {
    }

    private static ComponentFactory INSTANCE = new XmlComponentFactory();

    public static ComponentFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ComponentInfo> parse() throws Exception {
        List<ComponentInfo> componentInfoList = new ArrayList<>();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configFile);
        NodeList componentNodeList = document.getElementsByTagName("Component");
        for (int i = 0; i < componentNodeList.getLength(); i++) {
            Element componentNode = (Element) (componentNodeList.item(i));
            ComponentInfo componentInfo = new ComponentInfo();
            componentInfo.name = getChildNodeValue(componentNode, "Name");
            componentInfo.clazz = getChildNodeValue(componentNode, "Clazz");
            componentInfo.version = getChildNodeValue(componentNode, "Version");
            componentInfo.type = getChildNodeValue(componentNode, "Type");
            componentInfo.mode = getChildNodeValue(componentNode, "Mode");
            String restMode = getChildNodeValue(componentNode, "ResetMode");
            if (!TextUtils.isEmpty(restMode)) {
                componentInfo.resetMode = restMode;
            }
            String deep = getChildNodeValue(componentNode, "Deep");
            if (!TextUtils.isEmpty(deep)) {
                componentInfo.deep = Integer.parseInt(deep);
            }
            componentInfoList.add(componentInfo);
        }
        return componentInfoList;
    }

    @Override
    public boolean dump(List<ComponentInfo> componentInfoList) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element componentsNode = document.createElement("Components");
            document.appendChild(componentsNode);
            for (int i = 0; i < componentInfoList.size(); i++) {
                ComponentInfo componentInfo = componentInfoList.get(i);
                Element componentNode = document.createElement("Component");
                componentNode.appendChild(createChildNode(document, "Name", componentInfo.name));
                componentNode.appendChild(createChildNode(document, "Clazz", componentInfo.clazz));
                componentNode.appendChild(createChildNode(document, "Version", componentInfo.version));
                componentNode.appendChild(createChildNode(document, "Type", componentInfo.type));
                componentNode.appendChild(createChildNode(document, "Mode", componentInfo.mode));
                componentNode.appendChild(createChildNode(document, "Deep", String.valueOf(componentInfo.deep)));
                if (!TextUtils.isEmpty(componentInfo.resetMode)) {
                    componentNode.appendChild(createChildNode(document, "ResetMode", componentInfo.resetMode));
                }
                componentsNode.appendChild(componentNode);
            }
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty("version", "1.0");
            tf.setOutputProperty("encoding", "UTF-8");
            tf.setOutputProperty("indent", "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tf.transform(new DOMSource(document), new StreamResult(configFile));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Node createChildNode(Document document, String name, String value) {
        Element element = document.createElement(name);
        element.setNodeValue(value);
        element.setTextContent(value);
        return element;
    }

    private static String getChildNodeValue(Element parent, String name) {
        NodeList nodeList = parent.getElementsByTagName(name);
        if (nodeList == null || nodeList.getLength() < 1) return null;
        return nodeList.item(0).getFirstChild().getNodeValue();
    }
}
