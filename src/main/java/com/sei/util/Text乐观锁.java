package com.sei.util;

import com.sei.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Text乐观锁 {
    public static void main(String[] args) {
       /* ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        UserService userService = (UserService) applicationContext.getBean("UserServiceImpl");
        //创建一个固定线程池
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        Map<String, Object> map = new HashMap<>();
        map.put("", "");
        //模拟并发
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {

                    return userService.addUser(map);
                }
            });
        }*/

    }
}
