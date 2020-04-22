package com.myfirstDemo.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.extension.responsetemplating.RequestTemplateModel;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

@SpringBootTest
class DemoApplicationTests {

    private int port = 8089;
    private String realServer = "https://jsonplaceholder.typicode.com";
    private String body = "";
    WireMockServer wireMockServer = new WireMockServer(8090);
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);
// Do some stuff

    @Test
    void contextLoads() {
    }

    @Test
    @Description("Test a user from external service")
    public void testUser() {

        //Setup the endpoint
        wireMockServer.stubFor(get(urlPathMatching("/users/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)
                )

        );
        //Make a request
        RestTemplate requestTemplateModel = new RestTemplate();
        String resourceURL = realServer;
        ResponseEntity<String> response = requestTemplateModel
                .getForEntity(resourceURL + "/users/1", String.class);
        //Verify
        assertNotNull(response);

    }


}
