package org.exercise.springboot.controller;

import org.exercise.springboot.bean.Railine;
import org.exercise.springboot.bean.Station;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class StationController {
    @RequestMapping("/go")
    public String go() {
    //访问顺序：先找controller，没有就找视图解析器
        return "index";//因为找不到链接，会返回404页面
    }

    @RequestMapping("/add")
    public String add() {
        return "save";
    }

    @PostMapping("/savestation")
    @ResponseBody
    public String saveMonster(Station station) {
        System.out.println("保存车站-" + station);
        return "success";
    }

    //返回Station数据-要求以json格式返回
    @GetMapping("/getstation")
    @ResponseBody
    public Station getStation() {
        //老师说明
        //开发中, station对象是从DB获取，此处仅做模拟
        Station station = new Station();
        station.setId(16523);
        station.setName("利国");
        station.setCode("UVH");
        station.setIsCoach(false);
        station.setStart(new Date(10,0,2));
        Railine railine = new Railine();
        railine.setName("京沪线");
        railine.setDistance(766.05);
        station.setRailine(railine);
        return station;
    }

}
