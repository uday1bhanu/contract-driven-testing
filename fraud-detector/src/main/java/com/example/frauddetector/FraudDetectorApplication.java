package com.example.frauddetector;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableBinding(Source.class)
public class FraudDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectorApplication.class, args);
	}
}

@RestController
class FraudController{

	private final Source source;

	FraudController(Source source) {
		this.source = source;
	}

	@GetMapping("/frauds")
	ResponseEntity<List<String>> frauds(){
		return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList("Marcin", "Josh"));
	}

	@PostMapping("/trigger")
	void trigger(){
		this.source.output().send(MessageBuilder.withPayload(new Fraud("m")).build());
	}
}

@Data class Fraud {
	private final String surname;
}