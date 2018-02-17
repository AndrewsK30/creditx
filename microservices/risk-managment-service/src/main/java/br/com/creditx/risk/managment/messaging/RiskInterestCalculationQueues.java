package br.com.creditx.risk.managment.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RiskInterestCalculationQueues {

    public static final String CUSTOMER_INTEREST_PERCENATGE_CALCULATE_INPUT = "customer-interest-percentage-calculate-input";
    public static final String CUSTOMER_INTEREST_PERCENATGE_CALCULATE_DONE_OUTPUT = "customer-interest-percentage-calculate-done-output";

    @Input(CUSTOMER_INTEREST_PERCENATGE_CALCULATE_INPUT)
    SubscribableChannel input();

    @Output(CUSTOMER_INTEREST_PERCENATGE_CALCULATE_DONE_OUTPUT)
    MessageChannel output();
}
