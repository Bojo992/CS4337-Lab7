package com.example.demo.service;

import com.example.demo.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

@TestPropertySource("classpath:application-test.properties")
public class ExternalServiceIntegrationTest {
    private UserController userController;
    private ExternalService externalService;
    @Mock
    private RestTemplateBuilder restTemplateBuilderMocked;
    @Mock
    private RestTemplate restTemplateMocked;
    @Mock
    private ResponseEntity<String> responseEntityMocked;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(restTemplateBuilderMocked.build()).thenReturn(restTemplateMocked);
        when(restTemplateMocked.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntityMocked);
        when(responseEntityMocked.getBody()).thenReturn("test\n");
        when(responseEntityMocked.getStatusCode()).thenReturn(HttpStatus.OK);

        externalService = new ExternalService(restTemplateBuilderMocked);
        userController = new UserController(null, externalService);
    }

    @Test
    public void testMockedExternalService() {
        //arrange
        var expectedReturn = "<div style=\"background-color: #5a8949; padding: 20px; border-radius: 10px; box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.7); max-width: 400px; text-align: center; align-content: center; margin: auto;\">\n" +
                "    <h2>Card Title</h2>\n" +
                "    test\n" +
                "</div>";

        //act
        var response = userController.getLoremIpsum();

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBody(), expectedReturn);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}
