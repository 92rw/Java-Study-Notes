package com.javaweb.session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {
    public static void main(String[] args) {
        String content = "FRESH STRAWBERRY ® (*)\n" +
                "The Original Topped with Glazed Fresh Strawberries. Our Most Popular Flavor for 28 Years!\n" +
                "WHITE CHOCOLATE RASPBERRY TRUFFLE (****) \n" +
                "Creamy Cheesecake Swirled with White Chocolate and Raspberry\n" +
                "GODIVA CHOCOLATE CHEESECAKE   (**)\n" +
                "Layers of Flourless Godiva Chocolate Cake, Godiva Chocolate Cheesecake and Chocolate Mousse\n" +
                "FRESH BANANA CREAM CHEESECAKE (***)\n" +
                "Banana Cream Cheesecake Topped with Bavarian Cream and Fresh Sliced Bananas\n" +
                "DULCE DE LECHE CARAMEL CHEESECAKE   (***)\n" +
                "Caramel Cheesecake Topped with Caramel Mousse on a Vanilla Crust\n" +
                "TIRAMISU CHEESECAKE  (***)\n" +
                "Our Wonderful Cheesecake and Tiramisu Combined into one Amazing Dessert!\n" +
                "CHOCOLATE MOUSSE CHEESECAKE (**)\n" +
                "Silky Chocolate Cheesecake Topped with a Layer of Belgian Chocolate Mousse\n" +
                "VANILLA BEAN CHEESECAKE® (*)\n" +
                "Layers of Creamy Vanilla Bean Cheesecake, Vanilla Mousse and Whipped Cream\n" +
                "KAHLUA COCOA COFFEE CHEESECAKE   (***)\n" +
                "Layers of Rich Brownie, Kahlua Cheesecake, Creamy Chocolate Mousse and Chocolate Ganache\n" +
                "ADAM'S PEANUT BUTTER CUP FUDGE RIPPLE (****)\n" +
                "Creamy Cheesecake Swirled with Caramel, Peanut Butter, Butterfingers and Reeses Peanut Butter Cups\n" +
                "CHOCOLATE OREO MUDSLIDE CHEESECAKE (**) \n" +
                "Chocolate Oreos Baked in our Creamy Chocolate Cheesecake with a Chocolate-Almond Brownie Crust\n" +
                "CHOCOLATE TUXEDO CREAM CHEESECAKE   (**)\n" +
                "Layers of our Fudge Cake, Chocolate Cheesecake, Vanilla Mascarpone Mousse and Chocolate\n" +
                "DUTCH APPLE CARAMEL STREUSEL (**)   \n" +
                "Our Original Cheesecake, Baked Apples, Caramel and Brown Sugar Cinnamon Walnut Streusel \n" +
                "BROWNIE SUNDAE CHEESECAKE (***)\n" +
                "Brownie, Cheesecake, White Chocolate Mousse and Chocolate, Covered with Hot Fudge and Almonds\n" +
                "CHOCOLATE PEANUT BUTTER COOKIE-DOUGH (****)\n" +
                "Chocolate Cheesecake Loaded with Peanut Butter Cookie Dough and Topped with Chocolate\n" +
                "KEY LIME CHEESECAKE® (*)\n" +
                "Key Lime Pie in a Cheesecake! Deliciously Tart and Creamy on a Vanilla Crumb Crust\n" +
                "LEMON RASPBERRY CREAM CHEESECAKE   (**)\n" +
                "Raspberry Vanilla Cake, Creamy Lemon Cheesecake, Raspberry Lady Fingers and Lemon Mousse\n" +
                "CHOCOLATE RASPBERRY TRUFFLE (**)\n" +
                "Layers of Chocolate Cake, Chocolate-Raspberry Swirl Cheesecake, Chocolate Mousse and Chocolate Ganache\n" +
                "CHOCOLATE CHIP COOKIE-DOUGH CHEESECAKE (****)\n" +
                "Creamy Cheesecake Loaded with our Chocolate Chip Cookie-Dough and Topped with Walnuts\n" +
                "SNICKERS BAR CHUNKS AND CHEESECAKE (***)\n" +
                "Snickers Bar Baked right into Our Creamy Cheesecake and Topped with Fudge and Caramel\n" +
                "CRAIG'S CRAZY CARROT CAKE CHEESECAKE (**) \n" +
                "Carrot Cake and Cheesecake Swirled Together, Topped with Cream Cheese Icing and Roasted Almonds\n" +
                "OREO CHEESECAKE (****)\n" +
                "Lots of Oreos Baked into Our Creamy Cheesecake\n" +
                "CARAMEL PECAN TURTLE (*)\n" +
                "Pecan Brownie and Caramel Fudge Swirl Cheesecake, Topped with Caramel Turtle Pecans and Chocolate\n" +
                "CHOCOLATE COCONUT CREAM CHEESECAKE (***)\n";

        String exp = "\\((.*)\\)";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(content);
        int i = 0;
        System.out.println("=====匹配到的回文=====");
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
