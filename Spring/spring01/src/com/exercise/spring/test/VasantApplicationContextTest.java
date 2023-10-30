package com.exercise.spring.test;

import com.exercise.spring.Vasant.VasantApplicationContext;
import com.exercise.spring.Vasant.VasantApplicationContext2;
import com.exercise.spring.Vasant.VasantConfig;
import com.exercise.spring.bean.Monster;

public class VasantApplicationContextTest {
    public static void main(String[] args) throws Exception{
        VasantApplicationContext2 ioc = new VasantApplicationContext2(VasantConfig.class);
    }
}
