package com.mbr.admin.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmountUtils {
    public AmountUtils() {
    }

    public static String amount2display(String amount, int decimal) {
        String displayAmount = "";
        Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
        Matcher matcher = pattern.matcher(amount);
        if (matcher.matches()) {
            BigDecimal amountNumber = new BigDecimal(amount);
            displayAmount = amountNumber.divide((new BigDecimal(10)).pow(decimal), 3, RoundingMode.FLOOR).toString();
            return displayAmount;
        } else {
            return "0.000";
        }
    }

    public static String display2amount(String disPlayAmount, int decimal) {
        String amount = "";
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,3})?{1}");
        Matcher matcher = pattern.matcher(disPlayAmount);
        if (matcher.matches()) {
            amount = (new BigDecimal(disPlayAmount)).multiply((new BigDecimal(10)).pow(decimal)).setScale(0, RoundingMode.FLOOR).toString();
            return amount;
        } else {
            return "0";
        }
    }

    public static void main(String[] args) {
        String amount = (new BigDecimal("10000000000000000000000000000")).toString();
        System.out.println(amount2display(amount, 18));
        System.out.println(amount2display("1", 2));
        System.out.println(amount2display("123", 2));
        System.out.println(amount2display("1234", 2));
        System.out.println(amount2display("12345", 2));
        System.out.println(amount2display("123456", 2));
        System.out.println(amount2display("123456", 4));
        System.out.println(display2amount("2.033", 18));
        System.out.println(display2amount("2.03", 18));
        System.out.println(display2amount("2.1", 18));
        System.out.println(display2amount("2", 18));
        System.out.println(display2amount("0.010", 2));
        System.out.println(display2amount("0.011", 2));

    }
}
