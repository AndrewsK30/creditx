package br.com.creditx.customer.messaging;


import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.service.CreditRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class RiskInterestCalculationListener {

    @Autowired
    private CreditRiskService customerService;

    @StreamListener(RiskInterestCalculationQueues.CUSTOMER_INTEREST_PERCENATGE_CALCULATE_DONE_INPUT)
    public void receiveMessage(CreditRiskModel message){
        customerService.saveCustomer(message);
    }


}