package org.exercise.entity;

import java.util.Date;

public class Station {
    private Integer id;
    private String name;
    private Integer code;
    private Integer cargo;
    private Date openday;
    private double distance;

    public Station() {
    }

    public Station(Integer id, String name, Integer code, Integer cargo, Date openday, double distance) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cargo = cargo;
        this.openday = openday;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public Date getOpenday() {
        return openday;
    }

    public void setOpenday(Date openday) {
        this.openday = openday;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", cargo=" + cargo +
                ", openday=" + openday +
                ", distance=" + distance +
                '}';
    }
}
