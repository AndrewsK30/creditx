package br.com.creditx.customer.model;

import br.com.creditx.customer.type.RiskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRiskModel {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    private BigDecimal creditLimit;
    @NotNull
    private RiskType riskType;

    private BigDecimal interestPercentage;

}
