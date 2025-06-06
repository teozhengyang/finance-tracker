package com.zhengyang.backend.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhengyang.backend.domain.enums.AssetType;

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
public class AssetDto {
  private Long id;
  
  @NotNull(message = "Asset type is required")
  private AssetType type;
  
  @NotNull(message = "Description is required")
  @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
  private String description;
  
  @Size(max = 50, message = "Symbol must not exceed 50 characters")
  private String symbol;
  
  @NotNull(message = "Value is required")
  @Positive(message = "Value must be positive")
  private BigDecimal value;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // User reference
  private Long userId;
  private String username;
  
  // Goal reference
  private Long goalId;
  private String goalTitle;
}
