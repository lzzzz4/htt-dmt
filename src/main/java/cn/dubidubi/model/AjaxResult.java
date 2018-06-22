package cn.dubidubi.model;

import java.io.Serializable;

/**
 * @author lzj
 * @Description: ajax返回通用对象
 * @date 18-6-22下午1:59
 */
public class AjaxResult implements Serializable {
    private String message;
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
