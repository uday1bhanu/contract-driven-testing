package com.example.carrental;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableBinding(Sink.class)
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}
}

@Component
class FraudListener {
	Fraud fraud;

	@StreamListener(Sink.INPUT)
	void listen(Fraud fraud){
		System.out.print("\n\n\n FRAUD !!! ["+fraud+"]\n\n\n");
		this.fraud = fraud;
	}
}

@Data
class Fraud {
	private String surname;
	@JsonCreator
	public Fraud(final @JsonProperty("surname") String surname) {
		this.surname = surname;
	}
}