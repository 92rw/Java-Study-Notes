package org.exercise.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RequestController {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        request.setAttribute("user", "92rw");//向request域中添加的数据
        request.getSession().setAttribute("website", "https://92rw.github.io/"); //向session中添加数据
        return "forward:/ok";  //请求转发到  /ok
    }

    @GetMapping("/ok")//接收上面方法传递来的信息
    @ResponseBody
    public String ok( HttpServletRequest request,
            @SessionAttribute(value = "website", required = false) String website,
            @RequestAttribute(value = "user", required = false) String username
            ) {
        //获取到request域中的数据
        System.out.println("username-" + username);
        System.out.println("website-" + website);
        System.out.println("通过servlet api 获取 username-" + request.getAttribute("user"));
        System.out.println("通过servlet api 获取 website-" + request.getSession().getAttribute("website"));
        return "success";
    }

    //响应一个注册请求
    @GetMapping("/register")
    public String register(Map<String,Object> map, Model model,
                           HttpServletResponse response) {
        //如果一个注册请求,会将注册数据封装到map或者model
        //map中的数据和model的数据，会被放入到request域中
        map.put("calc1","2**16 = 65536");
        map.put("calc2","2**31 = 2147483647");
        model.addAttribute("2**8", 256);
        //一会我们再测试response使用
        //我们演示创建cookie,并通过response 添加到浏览器/客户端
        Cookie cookie = new Cookie("email", "hspedu@sohu.com");
        response.addCookie(cookie);

        //请求转发，检查下一个页面是否可以或得到这里的数据
        return "forward:/registerOk";
    }

    @ResponseBody
    @GetMapping("/registerOk")
    public String registerOk(HttpServletRequest request) {
        System.out.println("calc1-" + request.getAttribute("calc1"));
        System.out.println("calc2-" + request.getAttribute("calc2"));
        System.out.println("2**8-" + request.getAttribute("2**8"));
        return "success";
    }
}
