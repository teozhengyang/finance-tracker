package com.zhengyang.backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zhengyang.backend.domain.enums.TransactionCategory;
import com.zhengyang.backend.domain.enums.TransactionType;

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
@Table(name = "transactions")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
  private Long id;

  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  
  @NotNull(message = "Amount is required")
  @Positive(message = "Amount must be positive")
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;
  
  @NotNull(message = "Transaction date is required")
  @Column(nullable = false)
  private LocalDate date;

  @NotNull(message = "Category is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionCategory category;

  @NotNull(message = "Transaction type is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "from_asset_id")
  private AssetEntity fromAsset;

  @ManyToOne
  @JoinColumn(name = "to_asset_id")
  private AssetEntity toAsset;

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
