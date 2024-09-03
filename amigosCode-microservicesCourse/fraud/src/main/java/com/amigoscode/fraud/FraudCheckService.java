package com.amigoscode.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public boolean isFraudulentCustomer(Integer customerId){
        fraudCheckHistoryRepository.save(
                new FraudCheckHistory().builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}
