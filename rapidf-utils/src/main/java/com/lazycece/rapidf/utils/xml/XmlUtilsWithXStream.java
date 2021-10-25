/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.utils.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class XmlUtilsWithXStream {
    /**
     * object to xml string
     *
     * @param object object
     * @return String xml string
     */
    public static String objectToXml(Object object) {
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.processAnnotations(object.getClass());
        return xstream.toXML(object);
    }

    /**
     * xml string to object
     *
     * @param xmlStr xml string
     * @param clazz  clazz
     * @return object
     */
    public static <T> T xmlToObject(String xmlStr, Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(clazz);
        return (T) xstream.fromXML(xmlStr);
    }

    /**
     * xml string to map
     *
     * @param xmlString xml string
     * @return map
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException IOException
     * @throws SAXException SAXException
     */
    public static Map<String, Object> xmlToMap(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(xmlString)) {
            return map;
        }
        ByteArrayInputStream is = new ByteArrayInputStream(xmlString.getBytes());
        Document document = builder.parse(is);
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        int i = 0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
            i++;
        }
        return map;
    }
}
