import java.util.HashSet;
import java.util.Objects;

public class Studynote56_HashSetExercise {
//hashCode的重写比较的是两个值的地址，所以其中一个值是一个类的时候要先重写那个类的hashCode
}
/*
课堂练习1
定义一个Employee类，该类包含：private成员属性name,age要求：
1.创建3个Employee放入HashSet中
2.当 name和age的值相同时，认为是相同员工,不能添加到HashSet集合中

解题思路：
重写hashcode是使得相同对象对应同一个hash值，重写equals是不让其进行传统的地址比较而是名字和年龄比较

 */
class HashSetExercise1{
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add(new HashSetEmployee1("Toyama",18));
        hashSet.add(new HashSetEmployee1("Ishigawa",17));
        hashSet.add(new HashSetEmployee1("Toyama",18));

        System.out.println("hashSet = " + hashSet);

    }
}
class HashSetEmployee1{
    private String name;
    private int age;

    public HashSetEmployee1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override//你的equals方法是公开的，它会自动调用属性，所以不用写getset
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashSetEmployee1 that = (HashSetEmployee1) o;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "HashSetEmployee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}

/*
课堂练习2
定义一个Employee类，该类包含：private成员属性name,sal,birthday，
其中birthday为 HashSetBirthDate类型(属性包括：year,month,day),要求
1.创建3个Employee放入 HashSet中
2.当name和birthday的值相同时，认为是相同员工，不能添加到HashSet集合中

//解题思路: 重写hashCode(){ return Objects.hash(name, birthday.toString());}
 */
class HashSetExercise2{
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add(new HashSetEmployee2("Fukui",393,new HashSetBirthDate(1919,8,10)));
        hashSet.add(new HashSetEmployee2("Yamanashi",395,new HashSetBirthDate(1919,8,10)));
        hashSet.add(new HashSetEmployee2("Nagano",397,new HashSetBirthDate(1919,8,10)));
        hashSet.add(new HashSetEmployee2("Nagano",399,new HashSetBirthDate(1919,8,10)));

        System.out.println("hashSet = " + hashSet);
    }
}


class HashSetBirthDate{
    private int year;
    private int month;
    private int day;

    public HashSetBirthDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashSetBirthDate myDate = (HashSetBirthDate) o;
        return year == myDate.year &&
                month == myDate.month &&
                day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public String toString() {
        return String.format("%s年%s月%s日",year,month,day);
    }
}

class HashSetEmployee2{
    private String name;
    private double salary;
    private HashSetBirthDate bir;

    public HashSetEmployee2(String name, double salary, HashSetBirthDate bir) {
        this.name = name;
        this.salary = salary;
        this.bir = bir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashSetEmployee2 that = (HashSetEmployee2) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(bir, that.bir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bir);
    }

    @Override
    public String toString() {
        return "HashSetEmployee2{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", bir=" + bir +
                '}';
    }
}