package com.amigoscode.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {
    private FraudCheckService fraudCheckService;

    @GetMapping(path="{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId")Integer customerId){
        boolean isFraudulentCustomer=fraudCheckService.isFraudulentCustomer(customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
