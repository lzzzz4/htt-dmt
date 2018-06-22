package cn.dubidubi.controller.test;

import cn.dubidubi.model.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lzj
 * @Description: 用于htt测试连接
 * @date 18-6-22下午1:57
 */
@Controller
@RequestMapping("/v1")
public class TestController {

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    @CrossOrigin(value = "*", methods = {RequestMethod.GET})
    @ResponseBody
    /**
     * @Description:get 方法查询
     * @param [name]
     * @return cn.dubidubi.model.AjaxResult
     * @author lzj
     * @date 18-6-22 下午2:07
     */
    public AjaxResult getTest(@PathVariable("name") String name) {
        AjaxResult AjaxResult = new AjaxResult();
        AjaxResult.setCode(200);
        AjaxResult.setMessage("get查询" + name);
        return AjaxResult;
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.POST)
    @CrossOrigin(value = "*", methods = {RequestMethod.POST})
    @ResponseBody
    /**
     * @Description:get 方法新增
     * @param [name]
     * @return cn.dubidubi.model.AjaxResult
     * @author lzj
     * @date 18-6-22 下午2:07
     */
    public AjaxResult postTest(@PathVariable("name") String name, HttpServletRequest request) {
        Cookie[] Cookies = request.getCookies();
        for (int i = 0; i < Cookies.length; i++) {
            Cookie cookie = Cookies[i];
            System.out.println(cookie.getValue());
        }
        AjaxResult AjaxResult = new AjaxResult();
        AjaxResult.setCode(200);
        AjaxResult.setMessage("post新增" + name);
        return AjaxResult;
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.PUT)
    @CrossOrigin(value = "*", methods = {RequestMethod.PUT})
    @ResponseBody
    /**
     * @Description:get 方法更新
     * @param [name]
     * @return cn.dubidubi.model.AjaxResult
     * @author lzj
     * @date 18-6-22 下午2:07
     */
    public AjaxResult putTest(@PathVariable("name") String name, HttpSession session) {
        AjaxResult AjaxResult = new AjaxResult();
        AjaxResult.setCode(200);
        AjaxResult.setMessage("put更新" + name);
        return AjaxResult;
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.DELETE)
    @CrossOrigin(value = "*", methods = {RequestMethod.DELETE})
    @ResponseBody
    /**
     * @Description:get 方法删除
     * @param [name]
     * @return cn.dubidubi.model.AjaxResult
     * @author lzj
     * @date 18-6-22 下午2:07
     */
    public AjaxResult delTest(@PathVariable("name") String name) {
        AjaxResult AjaxResult = new AjaxResult();
        AjaxResult.setCode(200);
        AjaxResult.setMessage("delete删除" + name);
        return AjaxResult;
    }
}
