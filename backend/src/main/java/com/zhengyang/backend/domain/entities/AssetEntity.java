package com.zhengyang.backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.zhengyang.backend.domain.enums.AssetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "assets")
public class AssetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_id_seq")
  private Long id;

  @NotNull(message = "Asset type is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AssetType type;
  
  @NotNull(message = "Description is required")
  @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
  @Column(nullable = false)
  private String description;
  
  @Size(max = 50, message = "Symbol must not exceed 50 characters")
  private String symbol;
  
  @NotNull(message = "Value is required")
  @Positive(message = "Value must be positive")
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal value;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "goal_id")
  private GoalEntity goal;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
