package org.exercise.springboot.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ParameterController {
    //PathVariable需和链接匹配，和形参可以不一致
    //对于传入的多个形参，可以使用Map类型接收
    @GetMapping("/station/{id}/{name}")
    public String pathVariable(@PathVariable("id") Integer id,
                               @PathVariable("name") String code,
                               @PathVariable Map<Integer, String> map) {
        System.out.println("id-" + id);
        System.out.println("name-" + code);
        System.out.println("map-" + map);
        return "success";
    }


    /**
     * @RequestHeader("Host") 获取http请求头的 host信息
     * @RequestHeader Map<String, String> header: 获取到http请求的所有信息
     */
    @GetMapping("/requestHeader")
    public String requestHeader(@RequestHeader("host") String host,
                                @RequestHeader Map<String, String> header,//获取所有请求字段
                                @RequestHeader("accept") String accept) {
        System.out.println("host-" + host);
        System.out.println("header-" + header);
        System.out.println("accept-" + accept);
        return "success";
    }

    //对于相同参数名的多个值，需要使用集合接收
    //如果使用Map接收所有参数，同名多个参数只能接收第一个
    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name") String stationName,
                     @RequestParam("jurisdiction") List<String> jurisdiction,
                     @RequestParam Map<String, String> paras) {

        System.out.println("车站名-" + stationName);
        System.out.println("管辖机构-" + jurisdiction);
        System.out.println("paras-" + paras);
        return "success";
    }

    /**
     * 因为我的浏览器目前没有cookie，我们可以自己设置cookie[技巧还是非常有用]
     * 如果要测试，可以先写一个方法，在浏览器创建对应的cookie
     * 说明 1. value = "cookie_key" 表示接收名字为 cookie_key的cookie
     * 2. 如果浏览器携带来对应的cookie , 那么 后面的参数是String ,则接收到的是对应对value
     * 3. 后面的参数是Cookie ,则接收到的是封装好的对应的cookie
     * 配置注解的required属性，防止cookie不存在导致的异常
     */
    @GetMapping("/cookie")
    public String cookie(@CookieValue(value = "cookie_key", required = false) String cookie_value,
                         HttpServletRequest request,
                         @CookieValue(value = "username", required = false) Cookie cookie) {
        System.out.println("cookie_value-" + cookie_value);
        if (cookie != null) {
            System.out.println("username-" + cookie.getName() + "-" + cookie.getValue());
        }
        System.out.println("-------------------------");//第二种方法：使用Servlet原生方法获取
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie1 : cookies) {
            System.out.println(cookie1.getName() + "=>" + cookie1.getValue());
        }
        return "success";
    }

    /**
     * @RequestBody 是整体取出Post请求内容
     */
    @PostMapping("/save")
    public String postMethod(@RequestBody String content) {
        System.out.println("content-" + content);
        return "success";
    }
}
