package com.zhengyang.backend.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
public class GoalDto {
  private Long id;
  
  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
  private String title;
  
  @Size(max = 500, message = "Description must not exceed 500 characters")
  private String description;
  
  @NotNull(message = "Target amount is required")
  @Positive(message = "Target amount must be positive")
  private BigDecimal targetAmount;
  
  @NotNull(message = "Target date is required")
  @Future(message = "Target date must be in the future")
  private LocalDate targetDate;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // Progress calculation fields
  private BigDecimal currentAmount;
  private Double progressPercentage;
  
  // User reference
  private Long userId;
  private String username;
  
  // Associated assets (when requested)
  private Set<AssetDto> assets;
}
