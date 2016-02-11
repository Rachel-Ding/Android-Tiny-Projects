package com.dingding.readxml;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        try {
//            getAssets().open("Languages.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
//------------------------ 1 --------------------------------------------------
//            Document document = builder.parse(getAssets().open("languages.xml"));//将数据源转为Document对象
//            Element element = document.getDocumentElement();//取出<Languages标签中所有的内容（根元素）
//            NodeList list = element.getElementsByTagName("lan");//过滤读取“lan”标签的内容
//            for (int i = 0; i < list.getLength(); i++) {
//                Element lan = (Element) list.item(i);
//                text.append("\n" + lan.getAttribute("id"));//id标签内容
//                text.append("\n" + lan.getElementsByTagName("name").item(0).getTextContent());//name标签内容
//                text.append("\n" + lan.getElementsByTagName("ide").item(0).getTextContent());//ide标签内容
//            }


//------------------------ 2 --------------------------------------------------
            //创建XML文件数据结构
            Document newxml = builder.newDocument();
            Element languages = newxml.createElement("Languages");//取得根元素
            languages.setAttribute("cat", "it");//键值对

            //添加第一部分
            Element lan1 = newxml.createElement("lan");
            lan1.setAttribute("id", "1");
            Element name1 = newxml.createElement("name");
            name1.setTextContent("Java");
            Element ide1 = newxml.createElement("ide");
            ide1.setTextContent("Eclipse");
            //添加子元素
            lan1.appendChild(name1);
            lan1.appendChild(ide1);
            languages.appendChild(lan1);

            //添加第二部分
            Element lan2 = newxml.createElement("lan");
            lan2.setAttribute("id", "2");
            Element name2 = newxml.createElement("name");
            name2.setTextContent("C++");
            Element ide2 = newxml.createElement("ide");
            ide2.setTextContent("Visual Studio");
            //添加子元素
            lan2.appendChild(name2);
            lan2.appendChild(ide2);
            languages.appendChild(lan2);

            newxml.appendChild(languages);

            //转换为String类型来显示
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(newxml), new StreamResult(sw));

            text.setText(sw.toString());


//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
