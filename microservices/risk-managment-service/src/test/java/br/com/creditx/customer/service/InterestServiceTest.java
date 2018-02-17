package br.com.creditx.customer.service;


import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.type.RiskType;
import br.com.creditx.risk.managment.RiskManagemntApplication;
import br.com.creditx.risk.managment.messaging.RiskInterestCalculationListener;
import br.com.creditx.risk.managment.messaging.RiskInterestCalculationQueues;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiskManagemntApplication.class)
public class InterestServiceTest {

    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private RiskInterestCalculationQueues queueRiskCalculate;
    @Autowired
    public RiskInterestCalculationListener interestListener;

    @Test
    public void interestCalculationDone_RiskTypeA() throws Exception{
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();

        CreditRiskModel model = createCreditRiskModel();
        model.setRiskType(RiskType.A);
        interestListener.receiveMessage(model);

        Message<?> message = queue.take();
        model = (CreditRiskModel) message.getPayload();
        Assert.assertEquals(model.getInterestPercentage(),BigDecimal.valueOf(0));
    }

    @Test
    public void interestCalculationDone_RiskTypeB() throws Exception{
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();

        CreditRiskModel model = createCreditRiskModel();
        model.setRiskType(RiskType.B);
        interestListener.receiveMessage(model);

        Message<?> message = queue.take();
        model = (CreditRiskModel) message.getPayload();
        Assert.assertEquals(model.getInterestPercentage(),BigDecimal.valueOf(10));
    }

    @Test
    public void interestCalculationDone_RiskTypeC() throws Exception{
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();

        CreditRiskModel model = createCreditRiskModel();
        model.setRiskType(RiskType.C);
        interestListener.receiveMessage(model);

        Message<?> message = queue.take();
        model = (CreditRiskModel) message.getPayload();
        Assert.assertEquals(model.getInterestPercentage(),BigDecimal.valueOf(20));
    }

    private CreditRiskModel createCreditRiskModel(){
        return CreditRiskModel
                .builder()
                .creditLimit(BigDecimal.valueOf(2456.44))
                .name("Marcos")
                .riskType(RiskType.B)
                .build();
    }
}
