package com.zhengyang.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zhengyang.backend.domain.entities.GoalEntity;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

}
