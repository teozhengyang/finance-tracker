package com.zhengyang.backend.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhengyang.backend.domain.enums.BudgetPeriod;
import com.zhengyang.backend.domain.enums.TransactionCategory;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetDto {
  private Long id;
  
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  
  @NotNull(message = "Amount is required")
  @Positive(message = "Amount must be positive")
  private BigDecimal amount;

  @NotNull(message = "Category is required")
  private TransactionCategory category;
  
  @NotNull(message = "Budget period is required")
  private BudgetPeriod period;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // Budget tracking fields
  private BigDecimal spentAmount;
  private BigDecimal remainingAmount;
  private Double utilizationPercentage;
  
  // User reference
  private Long userId;
  private String username;
  
  // Asset reference
  private Long assetId;
  private String assetDescription;
}
