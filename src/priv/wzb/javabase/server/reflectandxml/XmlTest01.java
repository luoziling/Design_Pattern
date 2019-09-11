package priv.wzb.javabase.server.reflectandxml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * @author Satsuki
 * @time 2019/9/10 20:01
 * @description:
 * 熟悉SAX解析流程
 */
public class XmlTest01 {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //1.get parser Factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2.get parse from factory
        SAXParser parser = factory.newSAXParser();
        //3.编写处理器
        //4.加载文档Document注册处理器
        PHandler handler = new PHandler();
        //5.解析
        parser.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("priv/wzb/javabase/server/reflectandxml/p.xml"),
                handler);
    }
}
class PHandler extends DefaultHandler{
    @Override
    public void startDocument() throws SAXException {
        System.out.println("---start parse---");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"-->start parse");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if (contents.length()>0){
            System.out.println("contents:" + contents);

        }else {
            System.out.println("内容为空");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"-->end parse");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("---end parse---");
    }
}
