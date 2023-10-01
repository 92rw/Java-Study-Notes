package org.exercise.springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    //等价的写法
    //@RequestMapping(value = "/station", method = RequestMethod.GET)
    @GetMapping("/station")
    public String getStation() {
        return "GET-获取车站";
    }

    @DeleteMapping("/station")
    public String delStation() {
        return "DELETE-删除车站";
    }

    @PutMapping("/station")
    public String updateStation() {
        return "PUT-修改车站";
    }

    @PostMapping("/station")
    public String saveStation() {
        return "POST-保存车站";
    }
}
