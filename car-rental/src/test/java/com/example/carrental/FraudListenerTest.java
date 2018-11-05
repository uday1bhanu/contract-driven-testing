package com.example.carrental;

import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = "com.example:fraud-detector", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class FraudListenerTest {
    @Autowired
    FraudListener fraudListener;

    @Autowired
    Sink sink;

    @Autowired
    StubTrigger stubTrigger;

    @Test
    void should_store_fraud_for_messaging() {
        //given a fraud:
        Fraud fraud = new Fraud("m");

        //when a message is sent to channel:
        this.sink.input().send(MessageBuilder.withPayload(fraud).build());

        //then a fraud will be received:
        BDDAssertions.then(fraudListener.fraud).isNotNull();
        BDDAssertions.then(fraudListener.fraud.getSurname()).isEqualTo("m");
    }

    @Test
    void should_store_fraud_for_messaging_from_stub() {
        //fetch the stubs from producer contracts
        stubTrigger.trigger("trigger_fraud");

        //then a fraud will be received:
        BDDAssertions.then(fraudListener.fraud).isNotNull();
        BDDAssertions.then(fraudListener.fraud.getSurname()).isEqualTo("m");
    }
}