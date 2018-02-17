package br.com.creditx.customer.entity;


import br.com.creditx.customer.type.RiskType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreditRisk {
    private static final long serialVersionUID = -3003456732242241606L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="credit_risk_id_seq")
    @SequenceGenerator(name = "credit_risk_id_seq", sequenceName = "credit_risk_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal creditLimit;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RiskType riskType;

    @NotNull
    private BigDecimal interestPercentage;



}
