package br.com.creditx.risk.managment.service;

import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.type.RiskType;
import br.com.creditx.risk.managment.messaging.RiskInterestCalculationQueues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
//Service for calculate the InterestPercentage
@Service
public class InterestService {

    @Autowired
    private RiskInterestCalculationQueues queue;

    public void calculateInterest(CreditRiskModel customer){
        //TODO: calculate the interest?
        customer.setInterestPercentage(
                Arrays.stream(RiskType.values())
                .filter(e -> e.equals(customer.getRiskType()))
                .findFirst()
                .get()
                .getInterestPercentage()
        );
        queue.output().send(MessageBuilder.withPayload(customer).build());
    }
}
