package com.javaweb.session;

public class TestString {
    public static void main(String[] args) {
        String togo = "/views/manage/manage_menu.jsp";
        String pattern = "/views/manage";
        String nonesense = "/views/test";
        System.out.println(pattern.indexOf(togo));
        System.out.println(togo.indexOf(pattern));
        System.out.println(pattern.indexOf(nonesense));
        System.out.println(nonesense.indexOf(pattern));

    }

}
