package com.zhengyang.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zhengyang.backend.domain.entities.BudgetEntity;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
  
}
