package br.com.creditx.risk.managment.messaging;


import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.risk.managment.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class RiskInterestCalculationListener {

    @Autowired
    private InterestService interestService;


    @StreamListener(RiskInterestCalculationQueues.CUSTOMER_INTEREST_PERCENATGE_CALCULATE_INPUT)
    public void receiveMessage(CreditRiskModel message){
        interestService.calculateInterest(message);
    }


}
