package org.exercise.entity;

import lombok.Data;

@Data
public class RailStation {
    private Integer id;
    private String name;
    private Railway line;
}
