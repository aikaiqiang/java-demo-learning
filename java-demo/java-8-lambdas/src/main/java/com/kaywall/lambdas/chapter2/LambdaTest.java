package com.kaywall.lambdas.chapter2;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaTest {


    public void _1b(){
        Function<Double, Double> negate = (x) -> -1 * x;
        Function<Double, Double> square = (x) -> x * x;
        Function<Double, Double> percent = (x) -> 100 * x;
    }

    @Test
    public void _2b(){
        ThreadLocal<DateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA));
        DateFormat dateFormat = dateFormatThreadLocal.get();
        System.out.println(dateFormat.format(new Date(0)));
        assertEquals("1970-1-1", dateFormat.format(new Date(0)));
    }



    public static void main(String[] args) {

    }
}
