package com.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportApplicationTest {

    @Value("${spring.thymeleaf.prefix}")
    private String prefix;

    @Test
    public void valueTest() {
        System.out.println(prefix);
    }
}