package com.exercise.spring.bean;

public class Monster {
    private Integer momsterId;
    private String name;
    private String skill;

    public Monster(Integer momsterId, String name, String skill) {
        this.momsterId = momsterId;
        this.name = name;
        this.skill = skill;
    }

    public Integer getMomsterId() {
        return momsterId;
    }

    public void setMomsterId(Integer momsterId) {
        this.momsterId = momsterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Monster() {
        System.out.println("构造方法被调用");
    }

    @Override
    public String toString() {
        return "Monster{" +
                "momsterId=" + momsterId +
                ", name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
    public void init(){
        System.out.println("调用初始化 init()方法");
    }
    public void destroy(){
        System.out.println("调用销毁 destroy()方法");
    }

}
