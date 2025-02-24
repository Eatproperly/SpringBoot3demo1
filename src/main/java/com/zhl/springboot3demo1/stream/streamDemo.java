package com.zhl.springboot3demo1.stream;

import java.util.List;

public class streamDemo {
    public static void main(String[] args) {
        List.of("1", "2", "3").stream()
                .filter(str -> str.matches("-?\\d+(\\.\\d+)?"))
                .map(str->str+"a")
                .forEach(System.out::println);
    }
}
