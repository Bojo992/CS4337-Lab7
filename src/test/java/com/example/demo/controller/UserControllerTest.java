package com.example.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Test
    public void testBasic() {
        //arrange
        var userController = new UserController(null, null);

        //act
        var response = userController.basic();

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, "Spring Boot Demo is running");
    }
}
