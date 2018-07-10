package cn.dubidubi.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


/**
 * @author lzj
 * @Description:返回前端json数据
 * @date 18-7-9下午2:25
 */
@Controller
@RequestMapping("/echart/v1")
public class ShowEchartsExcelParse {
    @RequestMapping("{name}")
    @ResponseBody
    public String getOneEchartsByName(@PathVariable("name") String name) {
        System.out.println(name);
        return null;
    }

    public static void main(String[] args) {
        String a = "a";     //被双引号声明的在常量池中
        String b = "b";
        String c = "ab";
        String d = a + b;
        String e = a + "b";

        //如果d 生成很多个 那么这多个d是不是一样的
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        //intern 用在 字符串拼接中 去重用的 将所有的都放到常量池中
        for (int i = 0; i < 100; i++) {
            list1.add(a + b);
            list2.add((a + b).intern());
        }
        for (int i = 1; i < 100; i++) {
            System.out.println("list1 :");
            System.out.println(list1.get(i - 1) == list1.get(i));
            System.out.println("list2 :");
            System.out.println(list2.get(i - 1) == list2.get(i));
        }
        String intern = d.intern();
        System.out.println(c == intern);
        System.out.println(d.equals(c));
    }

}
