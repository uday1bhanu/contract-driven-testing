import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("trigger_fraud")

    input {
        triggeredBy("triggerFraud()")
    }

    outputMessage {
        sentTo("frauds")
        body([
                "surname" : "m"
        ])
        headers {
            messagingContentType(applicationJson())
        }
    }
}