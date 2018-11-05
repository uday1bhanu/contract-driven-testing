package com.example.barproducer;

import org.springframework.stereotype.Service;

@Service
public class AgeCheckingPersonCheckingService implements PersonCheckingService {
    @Override
    public Boolean shouldGetBeer(PersonToCheck personToCheck) {
        return null;
    }
}
