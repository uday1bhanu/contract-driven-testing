import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return a list of frauds")

    request {
        method GET()
        url "/frauds"
    }

    response {
        status OK()
        body(["Marcin", "Josh"])
    }

}