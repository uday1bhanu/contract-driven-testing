package com.example.barconsumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(
		ids = "com.example:bar-producer",
		stubsMode = StubRunnerProperties.StubsMode.LOCAL,
		stubsPerConsumer = true,
		consumerName = "bar-consumer"
)
public class BarConsumerApplicationTests {

	@StubRunnerPort("bar-producer")
	int port;

	@Test
	public void should_grant_a_beer_if_old_enough(){
		//givem:
		Request request = new Request("marcin", 22);

		//when:
		ResponseEntity<Response> responseResponseEntity = new RestTemplate().exchange(
				RequestEntity.post(URI.create("http://localhost:"+port+"/check"))
						.header("Content-Type", "application/json")
						.body(request), Response.class);

		//then:
		BDDAssertions.then(responseResponseEntity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(responseResponseEntity.getBody().getStatus()).isEqualTo("OK");
		BDDAssertions.then(responseResponseEntity.getBody().getName()).isEqualTo("marcin");
	}
}

@Data class Request {
	private String name;
	private int age;

	@JsonCreator
	public Request(final @JsonProperty("name") String name, final @JsonProperty("age") int age){
		this.name = name;
		this.age = age;
	}
}

@Data class Response {
	private String status;
	private String name;

	@JsonCreator
	public Response(final @JsonProperty("status") String status, final @JsonProperty("name") String name){
		this.status = status;
		this.name = name;
	}
}
