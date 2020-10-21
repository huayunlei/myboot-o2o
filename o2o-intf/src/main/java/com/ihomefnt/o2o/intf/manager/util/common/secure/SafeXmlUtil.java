package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Maps;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author liguolin
 * @create 2018-07-06 13:44
 **/
public class SafeXmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(SafeXmlUtil.class);

    public static DocumentBuilder documentBuilder;

    static{
        documentBuilder = buildWechatDocumentBuilder();
    }

    public static Map doXMLParse(InputStream inStream){

        Map<String, String> data = Maps.newHashMap();
        try {
            Document document = documentBuilder.parse(inStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                inStream.close();
            } catch (Exception ex) {
                logger.error("close instream exception",ex);
            }
        } catch (SAXException e) {
            logger.error("parse doc exception",e);
        } catch (IOException e) {
            logger.error("parse doc IO exception",e);
        }
        logger.info("parse wechat callback result map is {}", JSON.toJSONString(data));
        return data;
    }

    private static DocumentBuilder buildWechatDocumentBuilder() {

        DocumentBuilder documentBuilder = null;
        logger.info("=====================> build documentBuilder start ====================");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            documentBuilder =  documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.info("==========>ParserConfigurationException was thrown. The feature is probably not supported by your XML processor.");
        }
        logger.info("=====================> build documentBuilder success end ====================");
        return documentBuilder;
    }
}
