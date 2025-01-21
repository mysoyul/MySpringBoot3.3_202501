package com.basic.myspringboot.runner;

import com.basic.myspringboot.property.MyBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    String name;
    @Value("${myboot.age}")
    int age;

    @Autowired
    Environment environment;

    @Autowired
    MyBootProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyBootProperties getFullName = " +
                properties.getName());

        System.out.println("@Value myboot.name = " + name);
        System.out.println("@Value myboot.age = " + age);
        System.out.println("Environment getProperty fullName = " +
                environment.getProperty("myboot.fullName"));
        System.out.println("Environment getProperty port 번호 = " +
                environment.getProperty("local.server.port"));

        System.out.println("VM Argument foo " + args.containsOption("foo"));
        System.out.println("Program Argument bar "+ args.containsOption("bar"));
        //argument 목록 출력하기
        //forEach(Consumer) Consumer 의 추상메서드 void accept(T)
        //익명클래스
        args.getOptionNames().forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println(name);
            }
        });
        //람다식
        args.getOptionNames().forEach(name -> System.out.println(name));
        //Method Reference
        args.getOptionNames().forEach(System.out::println);

        if(args.containsOption("local.server.port")){
            args.getOptionValues("local.server.port").forEach(System.out::println);
        }
    }
}
