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
                "age": $(regex("[2-9][0-9]"))
        ])
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body("""
{ "status": "OK", "surname": "marcin" }
""")
    }
}