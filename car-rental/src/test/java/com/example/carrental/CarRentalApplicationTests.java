package com.example.carrental;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureWireMock(port = 9876)
public class CarRentalApplicationTests {

	@Rule
	public StubRunnerRule rule = new StubRunnerRule()
			.downloadStub("com.example:fraud-detector:+:stubs:9876")
			.stubsMode(StubRunnerProperties.StubsMode.LOCAL);

//	@Test
//	public void shuld_return_a_list_of_frauds(){
//		//given: a producer app is running
//		String json = "[\"Marcin\", \"Josh\"]";
//		WireMock.stubFor(WireMock.get("/frauds")
//				.willReturn(WireMock.aResponse().withBody(json)));
//
//		//when: we send it to the producer
//		String response = new RestTemplate()
//				.getForObject("http://localhost:9876/frauds", String.class);
//
//		//then: we will get a list of frauds
//		BDDAssertions.then(response)
//				.isEqualTo(json);
//	}

	@Test
	public void shuld_return_a_list_of_frauds_from_stubs(){
		//given: a producer app is running
		String json = "[\"Marcin\",\"Josh\"]";

		//when: we send it to the producer
		String response = new RestTemplate()
				.getForObject("http://localhost:9876/frauds", String.class);

		//then: we will get a list of frauds
		BDDAssertions.then(response)
				.isEqualTo(json);
	}

	@Test
	public void shuld_return_a_list_of_frauds_from_runningapp(){
		//given: a producer app is running
		String json = "[\"Marcin\",\"Josh\"]";

		//when: we send it to the producer
		String response = new RestTemplate()
				.getForObject("http://localhost:9875/frauds", String.class);

		//then: we will get a list of frauds
		BDDAssertions.then(response)
				.isEqualTo(json);
	}

}
