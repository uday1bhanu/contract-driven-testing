import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method POST()
        url "/check"
        headers {
            contentType(applicationJson())
        }
        body([
                "name": "marcin",
                "age": 17
        ])
        bodyMatchers {
            jsonPath('$.age', byRegex("[0-1][0-9]"))
        }
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body("""
{ "status": "NOT_OK", "surname": "marcin" }
""")
    }
}