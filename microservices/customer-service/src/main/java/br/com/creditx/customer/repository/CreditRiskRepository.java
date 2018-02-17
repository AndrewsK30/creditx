package br.com.creditx.customer.repository;

import br.com.creditx.customer.entity.CreditRisk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRiskRepository extends JpaRepository<CreditRisk,Long> {
}
