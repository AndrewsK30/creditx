package br.com.creditx.risk.managment;

import br.com.creditx.risk.managment.messaging.RiskInterestCalculationQueues;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({RiskInterestCalculationQueues.class})
public class RiskManagemntApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskManagemntApplication.class, args);
	}
}
