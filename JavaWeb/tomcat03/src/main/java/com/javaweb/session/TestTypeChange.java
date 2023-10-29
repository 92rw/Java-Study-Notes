package com.javaweb.session;

public class TestTypeChange {
    public static void main(String[] args) {
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "Hello";
        b[1] = 2;
        System.out.println(a);

    }
}
