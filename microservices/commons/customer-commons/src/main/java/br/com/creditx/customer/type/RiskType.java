package br.com.creditx.customer.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum RiskType {
    A(BigDecimal.valueOf(0)),B(BigDecimal.valueOf(10)),C(BigDecimal.valueOf(20));

    private BigDecimal interestPercentage;
}
