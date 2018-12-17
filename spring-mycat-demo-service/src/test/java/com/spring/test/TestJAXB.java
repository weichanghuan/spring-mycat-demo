package com.spring.test;

import com.spring.demo.model.Person;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;


/**
 * test 1.6 jaxb
 */
public class TestJAXB {


    // 测试专用
    @Test
    public void selectByPrimaryKey() throws Exception {
        Person user = new Person();
        user.setId(1L);
        user.setAge(13);
        user.setName("危常焕");
        File file = new File("E://user.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        //格式化输出，即按标签自动换行，否则就是一行输出
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //设置编码（默认编码就是utf-）
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        //是否省略xml头信息，默认不省略（false）
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        StringWriter sw = new StringWriter();
        marshaller.marshal(user, sw);
        StringBuffer sb = new StringBuffer();
        sb.append(sw.toString());
        System.out.println(sb);

    }

    @Test
    public void getUserTest() throws Exception {
        File file = new File("E://user.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Person user = (Person) unmarshaller.unmarshal(file);
        System.out.println(user.getId());

    }
}
