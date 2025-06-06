package com.zhengyang.backend.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhengyang.backend.domain.enums.TransactionCategory;
import com.zhengyang.backend.domain.enums.TransactionType;

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
public class TransactionDto {
  private Long id;
  
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  
  @NotNull(message = "Amount is required")
  @Positive(message = "Amount must be positive")
  private BigDecimal amount;
  
  @NotNull(message = "Transaction date is required")
  private LocalDate date;

  @NotNull(message = "Category is required")
  private TransactionCategory category;
  
  @NotNull(message = "Transaction type is required")
  private TransactionType type;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // User reference - typically just include userId to avoid circular references
  private Long userId;
  private String username; // For display purposes
  
  // Asset references
  private Long fromAssetId;
  private String fromAssetDescription;
  private Long toAssetId;
  private String toAssetDescription;
}
