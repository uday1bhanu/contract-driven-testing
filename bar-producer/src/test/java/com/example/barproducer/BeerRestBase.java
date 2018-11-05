package com.example.barproducer;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

public abstract class BeerRestBase {
    @Before
    public void setup(){
        //Add mock implementation
        RestAssuredMockMvc.standaloneSetup(
                new ProducerController(personToCheck -> personToCheck.getAge() >= 20)
        );
    }
}
