package br.com.creditx.customer.service;

import br.com.creditx.customer.entity.CreditRisk;
import br.com.creditx.customer.messaging.RiskInterestCalculationQueues;
import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.repository.CreditRiskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CreditRiskService {

    @Autowired
    private CreditRiskRepository customerRepository;

    @Autowired
    private RiskInterestCalculationQueues queue;

    @Transactional
    public void saveCustomer(CreditRiskModel customer){
        customerRepository.save(
                CreditRisk.builder()
                .id(customer.getId())
                .name(customer.getName())
                .creditLimit(customer.getCreditLimit())
                .interestPercentage(customer.getInterestPercentage())
                .riskType(customer.getRiskType())
                .build()
        );
    }

    public void sendCustomerToCalculateQueue(CreditRiskModel customer){
        queue.output().send(MessageBuilder.withPayload(customer).build());
    }

    public CreditRisk getCustomer(Long customerId){
        return customerRepository.findOne(customerId);
    }

    public Page<CreditRisk> allCustomersPageable(Pageable page){
        return customerRepository.findAll(page);
    }

    public void deleteCustomer(Long id){
        customerRepository.delete(id);
    }

    public void deleteCustomer(List<Long> id){
        id.forEach(this::deleteCustomer);
    }

    public void updateCustomer(CreditRiskModel customer){
        sendCustomerToCalculateQueue(customer);
    }

}
