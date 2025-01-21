package com.basic.myspringboot.runner;

import com.basic.myspringboot.config.CustomVO;
import com.basic.myspringboot.property.MyBootProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    CustomVO customVO;

    Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Logger 구현객체명 = {}", logger.getClass().getName());
        logger.info("현재 활성된 CustomVO = {} ", customVO);
        logger.info("MyBootProperties getFullName = {}", properties.getFullName());

        logger.info("@Value myboot.name = {}", name);
        logger.info("@Value myboot.age = {}", age);
        logger.info("Environment getProperty fullName = {}", environment.getProperty("myboot.fullName"));
        logger.info("Environment getProperty port 번호 = {}", environment.getProperty("local.server.port"));

        logger.debug("VM Argument foo = {}", args.containsOption("foo"));
        logger.debug("Program Argument bar = {}", args.containsOption("bar"));
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
