package br.com.creditx.customer.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

//Queues for comunicate and receive from the the risk-managment-service
public interface RiskInterestCalculationQueues {

    public static final String CUSTOMER_INTEREST_PERCENATGE_CALCULATE_OUTPUT = "customer-interest-percentage-calculate-output";
    public static final String CUSTOMER_INTEREST_PERCENATGE_CALCULATE_DONE_INPUT = "customer-interest-percentage-calculate-done-input";

    @Input(CUSTOMER_INTEREST_PERCENATGE_CALCULATE_DONE_INPUT)
    SubscribableChannel input();

    @Output(CUSTOMER_INTEREST_PERCENATGE_CALCULATE_OUTPUT)
    MessageChannel output();
}
