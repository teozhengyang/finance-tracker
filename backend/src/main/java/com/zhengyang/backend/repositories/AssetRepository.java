package com.zhengyang.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zhengyang.backend.domain.entities.AssetEntity;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

}
