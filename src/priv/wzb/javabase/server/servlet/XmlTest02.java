package priv.wzb.javabase.server.servlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import priv.wzb.javabase.server.reflectandxml.Person;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/9/10 20:01
 * @description:
 * 熟悉SAX解析流程
 */
public class XmlTest02 {
    public static void main(String[] args) throws Exception {
        //1.get parser Factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2.get parse from factory
        SAXParser parser = factory.newSAXParser();
        //3.编写处理器
        //4.加载文档Document注册处理器
        WebHandler handler = new WebHandler();
        //5.解析
        parser.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("priv/wzb/javabase/server/servlet/web.xml"),
                handler);

        WebContext context = new WebContext(handler.getEntities(),handler.getMappings());

        //假设你输入了 /login
//        String className = context.getClz("/login");
        String className = context.getClz("/reg");
        Class clz = Class.forName(className);
        Servlet servlet = (Servlet) clz.getConstructor().newInstance();
        System.out.println(servlet);
        servlet.service();


//        List<Entity> entities = handler.getEntities();
//        System.out.println(entities.toString());
//        List<Mapping> mappings = handler.getMappings();
//        System.out.println(mappings.toString());
    }
}

class WebHandler extends DefaultHandler {

    private List<Entity> entities;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;//存储操作标签
    private boolean isMapping = false;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("---start parse---");
        entities = new ArrayList<>();
        mappings= new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"-->start parse");
        if(null!=qName){
            tag = qName;
            if (qName.equals("servlet")){
                entity = new Entity();
                isMapping = false;
            }else if (qName.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();



        if(tag!=null){

            if (isMapping){
                //操作servlet-mapping
                if (tag.equals("servlet-name")){
                    mapping.setName(contents);
                }else if (tag.equals("url-pattern")){
                    if (contents.length()>0){
                        mapping.addPattern(contents);
                    }
                }
            }else {
                //操作servlet

                if (tag.equals("servlet-name")){
                    entity.setName(contents);
                }else if (tag.equals("servlet-class")){
                    if (contents.length()>0){
                        entity.setClz(contents);
                    }
                }

            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"-->end parse");

        if (qName!=null) {
            if (qName.equals("servlet")){
                entities.add(entity);
            }else if (qName.equals("servlet-mapping")){
               mappings.add(mapping);
            }
        }
        tag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("---end parse---");
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
