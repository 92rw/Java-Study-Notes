package org.exercise.entity;

import lombok.Data;

@Data
public class StaCode {
    private Integer code;
    private String pinyin;
    private StaName staName;
}
