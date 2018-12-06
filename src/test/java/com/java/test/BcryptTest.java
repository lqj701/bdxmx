package com.java.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bdxmx.miniprogram.BdxmxMiniProgramApplication;
import com.ik.crm.commons.util.BCryptUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BdxmxMiniProgramApplication.class)
public class BcryptTest {

    @Test
    public void digest() {
        String password = "abc!23A";
        String passwordEncoded = BCryptUtil.digest(password);

        System.out.println("passwordEncoded = " + passwordEncoded);
        System.out.println("verify = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify1 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify2 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify3 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
    }

    @Test
    public void test() {
        String password = "duolaameng";
        String passwordEncoded = BCryptUtil.digest(password);
        // String passwordEncoded = "$2a$10$/cjKJs76dTZ4Su3jtqeYp.7NN4F4hH6gIH68pzgkf0CZSfq.IAxwm";

        System.out.println("passwordEncoded = " + passwordEncoded);
        System.out.println("verify = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify1 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify2 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
        System.out.println("verify3 = " + BCryptUtil.verifyPassword(passwordEncoded, password));
    }
}
