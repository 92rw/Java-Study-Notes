package com.exercise.spring.bean;

public class Train {
    public Train(Integer trainNo, String origin, String terminal) {
        this.trainNo = trainNo;
        this.origin = origin;
        this.terminal = terminal;
    }

    private Integer trainNo;
    private String origin;
    private String terminal;

    public Train() {
    }

    public Integer getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(Integer trainNo) {
        this.trainNo = trainNo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainNo='" + trainNo + '\'' +
                ", origin='" + origin + '\'' +
                ", terminal='" + terminal + '\'' +
                '}';
    }
}
