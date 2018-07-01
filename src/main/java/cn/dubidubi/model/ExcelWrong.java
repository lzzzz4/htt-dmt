package cn.dubidubi.model;

import java.io.Serializable;

/**
 * @author lzj
 * @Description:
 * @date 18-7-1下午4:15
 */
public class ExcelWrong implements Serializable {
    private String name;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExcelWrong{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
