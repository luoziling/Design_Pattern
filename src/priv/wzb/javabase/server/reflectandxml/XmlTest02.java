package priv.wzb.javabase.server.reflectandxml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //1.get parser Factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2.get parse from factory
        SAXParser parser = factory.newSAXParser();
        //3.编写处理器
        //4.加载文档Document注册处理器
        PersonHandler handler = new PersonHandler();
        //5.解析
        parser.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("priv/wzb/javabase/server/reflectandxml/p.xml"),
                handler);

        List<Person> persons = handler.getPersons();
        System.out.println(persons);
    }
}

class PersonHandler extends DefaultHandler {

    private List<Person> persons;
    private Person person;
    private String tag;//存储操作标签

    @Override
    public void startDocument() throws SAXException {
        System.out.println("---start parse---");
        persons = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"-->start parse");
        if(null!=qName){
            tag = qName;
        }

        if (qName.equals("person")){
            person = new Person();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if(tag!=null){

            if (tag.equals("name")){
                person.setName(contents);
            }else if (tag.equals("age")){
                if (contents.length()>0){
                    person.setAge(Integer.valueOf(contents));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"-->end parse");

        if (qName!=null) {
            if (qName.equals("person")){
                persons.add(person);
            }
        }
        tag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("---end parse---");
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
