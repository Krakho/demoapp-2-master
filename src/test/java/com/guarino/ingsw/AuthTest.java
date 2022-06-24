package com.guarino.ingsw;

import com.guarino.ingsw.controller.AuthController;
import com.guarino.ingsw.dto.RegisterRequest;
import com.guarino.ingsw.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
class AuthTest {

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Test
    void registrationTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "andre97guarino@hotmail.it";
        registerRequest.password = "123456password";
        registerRequest.username = "krakho";

        authController.signup(registerRequest);
        userRepository.findByUsername("krakho").orElseThrow(RuntimeException::new);

    }

    @Test
    void registrationFailureTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "";
        registerRequest.password = "123456password";
        registerRequest.username = "krakho";
        assertThrows(Exception.class, () -> authController.signup(registerRequest));


        registerRequest.email = "andre97guarino@hotmail.it";
        registerRequest.password = "";
        registerRequest.username = "krakho";
        assertThrows(Exception.class, () -> authController.signup(registerRequest));


        registerRequest.email = "andre97guarino@hotmail.it";
        registerRequest.password = "254tcf456cv245";
        registerRequest.username = "";
        assertThrows(Exception.class, () -> authController.signup(registerRequest));
    }


}
