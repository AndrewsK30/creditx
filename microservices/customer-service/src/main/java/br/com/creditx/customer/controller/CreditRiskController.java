package br.com.creditx.customer.controller;

import br.com.creditx.customer.entity.CreditRisk;
import br.com.creditx.customer.model.CreditRiskModel;
import br.com.creditx.customer.service.CreditRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit/risk")
public class CreditRiskController {

    @Autowired
    private CreditRiskService creditRiskService;

    @PostMapping
    public ResponseEntity newCustomers(@NotNull @Valid @RequestBody CreditRiskModel customerModel){
        if (customerModel.getId() != null)
            return ResponseEntity.badRequest().build();
        creditRiskService.sendCustomerToCalculateQueue(customerModel);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public Page<CreditRisk> listCustomers(Pageable page){
        return creditRiskService.allCustomersPageable(page);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Long customerId){
        CreditRisk creditRisk = creditRiskService.getCustomer(customerId);
        return Optional.of(creditRisk)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateCustomer(@NotNull @PathVariable("id") Long customerId,@NotNull @Valid @RequestBody CreditRiskModel customerModel){
        customerModel.setId(customerId);
        creditRiskService.updateCustomer(customerModel);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long customerId){
        creditRiskService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteCustomer(@RequestParam("ids") List<Long> customerId){
        creditRiskService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

}
