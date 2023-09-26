package com.javaweb.jsp;

public class Station {
    private Integer staNum;
    private String staName;
    private String pyCode;

    public Station(Integer staNum, String staName, String pyCode) {
        this.staNum = staNum;
        this.staName = staName;
        this.pyCode = pyCode;
    }

    public Integer getStaNum() {
        return staNum;
    }

    public void setStaNum(Integer staNum) {
        this.staNum = staNum;
    }

    public String getStaName() {
        return staName;
    }

    public void setStaName(String staName) {
        this.staName = staName;
    }

    public String getPyCode() {
        return pyCode;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode;
    }
}
