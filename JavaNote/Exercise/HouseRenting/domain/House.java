package Exercise.HouseRenting.domain;
//每个 House 的对象表示一个房屋信息

public class House {
    //储存的信息包括：编号，房主，地址，电话，月租，状态（未出租/已出租）
    private int id;
    private String name;
    private String address;
    private int phoneNum;
    private double monthRent;
    private String state;

    //构造器和方法


    public House(int id, String name, String address, int phoneNum, double monthRent, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.monthRent = monthRent;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public double getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(double monthRent) {
        this.monthRent = monthRent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //为了方便输出对象信息，实现toString

    @Override
    public String toString() {
        return id +
                "\t\t" + name +
                "\t\t" + address +
                "\t\t" + phoneNum +
                "\t" + monthRent +
                "\t" + state ;
    }
}
