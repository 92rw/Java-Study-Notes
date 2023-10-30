package com.exercise.spring.factory;

import com.exercise.spring.bean.Monster;

import java.util.HashMap;
import java.util.Map;

public class InstanceFactory {
    private Map<String, Monster> monsterMap;

    //使用普通代码块进行初始化
    {
        monsterMap = new HashMap<>();
        monsterMap.put("monster04", new Monster(68, "童威","出洞蛟"));
        monsterMap.put("monster05", new Monster(69, "童猛","翻江蜃"));
    }

    public Monster getMonster(String key) {
        return monsterMap.get(key);
    }
}
