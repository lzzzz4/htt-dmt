package cn.dubidubi.service;

import org.apache.commons.net.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author lzj
 * @Description: 标准的excel模版解析
 * @date 18-6-29上午10:52
 */
public interface SimpleExcelParseToJson {
    public static void main(String[] args) {
//        String string1 = "SEU";
//        String string2 = new String("SEU");
//        String str1 = string1 + string2;
//        System.out.println(str1.intern() == str1);
//        System.out.println(str1 == "SEUCalvin");
        String str2 = "SEUCalvin";//新加的一行代码，其余不变
        String str1 = new String("SEU")+ new String("Calvin");
        System.out.println(str1.intern() == str1);
        System.out.println(str1 == "SEUCalvin");
    }
}
