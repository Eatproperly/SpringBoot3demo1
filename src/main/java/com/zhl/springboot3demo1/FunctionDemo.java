package com.zhl.springboot3demo1;

import java.util.UUID;
import java.util.function.*;

public class FunctionDemo {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> "42";
        Predicate<String> predicate = str -> str.matches("-?\\d+(\\.\\d+)?");
        Function<String ,Integer> function = i -> Integer.parseInt(i);
        Consumer<Integer> consumer = integer -> {
            if(integer%2==0){
                System.out.println("偶数:"+integer);
            }
            else if(integer%2!=0){
                System.out.println("奇数:"+integer);
            }
        };

        Mydemo1(supplier, predicate, function, consumer);

        Mydemo1(()->"888a",str -> str.matches("-?\\d+(\\.\\d+)?"),Integer::parseInt,System.out::println);
    }

    private static void Mydemo1(Supplier<String> supplier, Predicate<String> predicate, Function<String, Integer> function, Consumer<Integer> consumer) {
        if(predicate.test(supplier.get())){
            consumer.accept(function.apply(supplier.get()));
        }
        else {
            System.out.println("数字非法");
        }
    }

    public static void aaa(String[] args) {
        Supplier<String> s = () -> UUID.randomUUID().toString();
        Supplier<String> supplier = () -> "42";
        Function<String ,Integer> function = i -> Integer.parseInt(i);
        Predicate<Integer> predicate = odd -> odd%2==0;
        Consumer<Integer> consumer = integer -> {
            if(predicate.test(integer)){
                System.out.println("偶数:"+integer);
            }
            else if(predicate.negate().test(integer)){
                System.out.println("奇数:"+integer);
            }
            else System.out.println("不是数字");
        };
        Mymethod(supplier, function, predicate);
        Mymethod(()->"777",i->Integer.parseInt(i),even->even%2==0);
    }

    private static void Mymethod(Supplier<String> supplier, Function<String, Integer> function, Predicate<Integer> predicate) {
        //串起来
        if(predicate.test(function.apply(supplier.get()))){
            System.out.println("偶数"+ function.apply(supplier.get()));
        } else if (predicate.negate().test(function.apply(supplier.get()))) {
            System.out.println("奇数:"+ function.apply(supplier.get()));
        }
        else {
            System.out.println("不是数字");
        }
    }
}
