package br.com.creditx.customer.web;


import br.com.creditx.customer.CustomerApplication;
import br.com.creditx.customer.entity.CreditRisk;
import br.com.creditx.customer.messaging.RiskInterestCalculationQueues;
import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.repository.CreditRiskRepository;
import br.com.creditx.customer.type.RiskType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerApplication.class)
public class CustomerControllerTest {

    @Autowired
    private CreditRiskRepository creditRiskRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private RiskInterestCalculationQueues queueRiskCalculate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc restMockMvc;

    @Before
    public void setup() {
        this.restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void initTest() {
        creditRiskRepository.deleteAll();
    }

    @Test
    public void createCreditLimitRequest_Sucess() throws Exception {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();
        CreditRiskModel creditRisk = createCreditRiskModel();
        restMockMvc.perform(post("/credit/risk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isNoContent());
        Message<?> message = queue.take();
        CreditRiskModel model = (CreditRiskModel) message.getPayload();
        Assert.assertNull(model.getId());
        Assert.assertEquals(creditRisk.getName(),model.getName());
        Assert.assertEquals(creditRisk.getCreditLimit(),model.getCreditLimit());
        Assert.assertEquals(creditRisk.getRiskType(),model.getRiskType());
        Assert.assertNull(model.getInterestPercentage());

    }

    @Test
    public void createCreditLimitRequest_BadRequest_withId() throws Exception {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();
        CreditRiskModel creditRisk = createCreditRiskModel();
        creditRisk.setId(1l);
        restMockMvc.perform(post("/credit/risk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isBadRequest());
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void createCreditLimitRequest_BadRequest_creditLimitNull() throws Exception {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();
        CreditRiskModel creditRisk = createCreditRiskModel();
        creditRisk.setCreditLimit(null);
        restMockMvc.perform(post("/credit/risk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isBadRequest());
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void createCreditLimitRequest_BadRequest_nameNull() throws Exception {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();
        CreditRiskModel creditRisk = createCreditRiskModel();
        creditRisk.setName(null);
        restMockMvc.perform(post("/credit/risk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isBadRequest());
        Assert.assertTrue(queue.isEmpty());

    }

    @Test
    public void editCreditLimitRequest_Sucess() throws Exception {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(queueRiskCalculate.output());
        queue.clear();
        CreditRiskModel creditRisk = createCreditRiskModel();
        creditRisk.setId(1l);
        restMockMvc.perform(put("/credit/risk/{id}", creditRisk.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isNoContent());
        Message<?> message = queue.take();
        CreditRiskModel model = (CreditRiskModel) message.getPayload();
        Assert.assertEquals(creditRisk.getId(),model.getId());
        Assert.assertEquals(creditRisk.getName(),model.getName());
        Assert.assertEquals(creditRisk.getCreditLimit(),model.getCreditLimit());
        Assert.assertEquals(creditRisk.getRiskType(),model.getRiskType());
        Assert.assertNull(model.getInterestPercentage());
    }

    @Test
    public void getCreditLimitRequest_Sucess() throws Exception {
        CreditRisk creditRisk = CreditRisk.builder()
                .creditLimit(BigDecimal.valueOf(22.22))
                .name("teste")
                .riskType(RiskType.C)
                .interestPercentage(BigDecimal.valueOf(20))
                .build();
        creditRisk = creditRiskRepository.save(creditRisk);
        restMockMvc.perform(get("/credit/risk/{id}", creditRisk.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(creditRisk.getId()))
                .andExpect(jsonPath("$.name").value(creditRisk.getName()))
                .andExpect(jsonPath("$.creditLimit").value(creditRisk.getCreditLimit().toPlainString()))
                .andExpect(jsonPath("$.interestPercentage").value(creditRisk.getInterestPercentage().floatValue()))
                .andExpect(jsonPath("$.riskType").value(creditRisk.getRiskType().toString()));

    }

    @Test
    public void deletCreditLimitRequest_Sucess() throws Exception {
        CreditRisk creditRisk = CreditRisk.builder()
                .creditLimit(BigDecimal.valueOf(22.22))
                .name("teste")
                .riskType(RiskType.C)
                .interestPercentage(BigDecimal.valueOf(20))
                .build();
        creditRisk = creditRiskRepository.save(creditRisk);
        restMockMvc.perform(delete("/credit/risk/{id}", creditRisk.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(creditRisk)))
                .andExpect(status().isOk());
        Assert.assertNull(creditRiskRepository.findOne(creditRisk.getId()));

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
