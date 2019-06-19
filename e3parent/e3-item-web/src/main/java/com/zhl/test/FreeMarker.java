package com.zhl.test;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

public class FreeMarker {

//    @Test
//    public  void  testFreeMarker() throws  Exception{
//        //创建一个模板文件(ftl)
//        //创建一个Configuration对象
//        Configuration configuration = new Configuration(Configuration.getVersion());
//        //设置模板文件保存目录
//        configuration.setDirectoryForTemplateLoading(new File("D:/distributed/e3parent/e3-item-web/src/main/webapp/WEB-INF/ftl"));
//        //模板文件的编码格式，一般是utf-8
//        configuration.setDefaultEncoding("UTF-8");
//        //加载模板文件，创建一个模板对象
//        /*Template template = configuration.getTemplate("hello.ftl");*/
//        Template template = configuration.getTemplate("student.ftl","utf-8");
//
//        //创建一个数据集，可以是pojo也可以是map，推荐使用map
//       Map data = new HashMap<>();
//       data.put("hello","hello freekMarker!");
//
//        //创建一个pojo对象
//        Student student = new Student(1,"小白",18,"梵蒂冈敢死队");
//        data.put("student",student);
//
//        //添加一个List
//        List<Student> studentList = new ArrayList<>();
//        studentList.add(new Student(1,"小白1",18,"梵蒂冈敢死队"));
//        studentList.add(new Student(2,"小白2",19,"梵蒂冈敢死队"));
//        studentList.add(new Student(3,"小白3",20,"梵蒂冈敢死队"));
//        studentList.add(new Student(4,"小白4",21,"梵蒂冈敢死队"));
//        studentList.add(new Student(5,"小白5",22,"梵蒂冈敢死队"));
//        studentList.add(new Student(6,"小白6",23,"梵蒂冈敢死队"));
//        studentList.add(new Student(7,"小白7",24,"梵蒂冈敢死队"));
//        studentList.add(new Student(8,"小白8",25,"梵蒂冈敢死队"));
//        studentList.add(new Student(9,"小白9",26,"梵蒂冈敢死队"));
//        data.put("studentList",studentList);
//        //添加日期
//        data.put("date",new Date());
//
//        //
//        //创建一个Writer对象，指定输出文件的路径进文件名
//        /*Writer out = new FileWriter(new File("D:/tmp/hello.txt"));*/
//        Writer out = new FileWriter(new File("D:/tmp/item/student3.html"));
//       /* FileOutputStream fileoutput = new FileOutputStream(new File("D:/tmp/student.html"));
//        Writer writer = new OutputStreamWriter(fileoutput,"UTF-8");*/
//        //生成静态页面
//          template.process(data,out);
//        //关闭流
//        out.close();
//    }
}
