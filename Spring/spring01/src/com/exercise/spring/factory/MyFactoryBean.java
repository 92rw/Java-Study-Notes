package com.exercise.spring.factory;

import com.exercise.spring.bean.Monster;
import org.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

public class MyFactoryBean implements FactoryBean<Monster> {
    private String key;//配置bean对象时，需要指定对象的key
    private Map<String, Monster> monsterMap;

    //使用普通代码块进行初始化
    {
        monsterMap = new HashMap<>();
        monsterMap.put("monster06", new Monster(64, "项充","八臂哪吒"));
        monsterMap.put("monster07", new Monster(65, "李衮","飞天大圣"));
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public Monster getObject() throws Exception {
        return monsterMap.get(key);
    }

    @Override
    public Class<?> getObjectType() {
        return Monster.class;
    }

    @Override
    public boolean isSingleton() {
        return true;//默认是false，此处设置为true
    }
}

