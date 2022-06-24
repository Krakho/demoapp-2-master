package com.guarino.ingsw;

import com.guarino.ingsw.dto.RegisterRequest;
import com.guarino.ingsw.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    AuthService authService;

    @Test
    public void registrationTest() {
        authService.signup(new RegisterRequest());
    }

    @Test
    void contextLoads() {
    }

}
