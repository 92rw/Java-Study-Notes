package org.exercise.entity;

import lombok.Data;

import java.util.List;

@Data
public class Railway {
    private Integer id;
    private String name;
    private List<RailStation> stations;
}
