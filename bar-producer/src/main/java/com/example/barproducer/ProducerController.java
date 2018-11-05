package com.example.barproducer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final PersonCheckingService personCheckingService;

    public ProducerController(PersonCheckingService personCheckingService){
        this.personCheckingService = personCheckingService;
    }

    @RequestMapping(
            value = "/check",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public Response check(@RequestBody PersonToCheck personToCheck){
        if(personCheckingService.shouldGetBeer(personToCheck)){
            return new Response(BeerCheckStatus.OK, personToCheck.getName());
        }
        return new Response(BeerCheckStatus.NOTOK, personToCheck.getName());
    }
}

interface PersonCheckingService {
    Boolean shouldGetBeer(PersonToCheck personToCheck);
}

@Data
class PersonToCheck {
    private String name;
    private int age;

    @JsonCreator
    public PersonToCheck(final @JsonProperty("name") String name, final @JsonProperty("age") int age){
        this.name = name;
        this.age = age;
    }
}

@Data class Response {
    private String status;
    private String name;
    private String surname;

    @JsonCreator
    public Response(final @JsonProperty("status") String status, final @JsonProperty("name") String name){
        this.status = status;
        this.name = name;
        this.surname = name;
    }
}

class BeerCheckStatus {
    public static final String OK = "OK";
    public static final String NOTOK = "NOT_OK";
}