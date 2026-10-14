package com.ylb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DormSystemApplicationTests {

    @Test
    void generatePassword() {
        // 直接 new 一个加密器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 生成加密串
        String pwd = encoder.encode("123456");
        
        System.out.println("=========================================");
        System.out.println("你的加密密码是：");
        System.out.println(pwd);
        System.out.println("=========================================");
    }
}