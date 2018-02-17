package br.com.creditx.customer.service;

import br.com.creditx.customer.CustomerApplication;
import br.com.creditx.customer.entity.CreditRisk;
import br.com.creditx.customer.messaging.RiskInterestCalculationListener;
import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.repository.CreditRiskRepository;
import br.com.creditx.customer.type.RiskType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerApplication.class)
public class CreditRiskServiceTest {

    @Autowired
    private RiskInterestCalculationListener riskInterestCalculationListener;
    @Autowired
    private CreditRiskRepository creditRiskRepository;

    @Before
    public void initTest() {
        creditRiskRepository.deleteAll();
    }


    @Test
    public void interestCalculationDone(){
        CreditRiskModel model = createCreditRiskModel();
        riskInterestCalculationListener.receiveMessage(model);

        CreditRisk creditRisk = creditRiskRepository.findAll().get(0);
        Assert.assertEquals(creditRisk.getName(),model.getName());
        Assert.assertEquals(creditRisk.getCreditLimit(),model.getCreditLimit());
        Assert.assertEquals(creditRisk.getRiskType(),model.getRiskType());
        Assert.assertEquals(creditRisk.getInterestPercentage().doubleValue(),model.getInterestPercentage().doubleValue(),2);

    }

    @Test
    public void nnterestCalculationDoneUpdate(){
        CreditRiskModel model = createCreditRiskModel();
        CreditRisk creditRisk = creditRiskRepository.save(
                CreditRisk.builder()
                        .riskType(model.getRiskType())
                        .creditLimit(model.getCreditLimit())
                        .name(model.getName())
                        .interestPercentage(model.getInterestPercentage())
                        .build()
        );
        //Trocando o nome para update
        model.setId(creditRisk.getId());
        model.setName("teste");
        riskInterestCalculationListener.receiveMessage(model);

        creditRisk = creditRiskRepository.findOne(model.getId());
        Assert.assertEquals(creditRisk.getName(),model.getName());
        Assert.assertEquals(creditRisk.getCreditLimit(),model.getCreditLimit());
        Assert.assertEquals(creditRisk.getRiskType(),model.getRiskType());
        Assert.assertEquals(creditRisk.getInterestPercentage().doubleValue(),model.getInterestPercentage().doubleValue(),2);

    }

    private CreditRiskModel createCreditRiskModel(){
        return CreditRiskModel
                .builder()
                .creditLimit(BigDecimal.valueOf(2456.44))
                .name("Marcos")
                .interestPercentage(BigDecimal.valueOf(20))
                .riskType(RiskType.B)
                .build();
    }
}
