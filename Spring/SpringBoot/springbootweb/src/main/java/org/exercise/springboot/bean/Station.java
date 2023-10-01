package org.exercise.springboot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Station {
    private Integer id;
    private String name;
    private String code;
    private Boolean isCoach;
    private Date start;
    private Railine railine;
}
