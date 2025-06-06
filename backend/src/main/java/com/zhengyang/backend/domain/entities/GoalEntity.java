package com.zhengyang.backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "goals")
public class GoalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goal_id_seq")
  private Long id;

  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
  @Column(nullable = false)
  private String title;
  
  @Size(max = 500, message = "Description must not exceed 500 characters")
  private String description;
  
  @NotNull(message = "Target amount is required")
  @Positive(message = "Target amount must be positive")
  @Column(name = "target_amount", nullable = false, precision = 19, scale = 2)
  private BigDecimal targetAmount;
  
  @NotNull(message = "Target date is required")
  @Future(message = "Target date must be in the future")
  @Column(name = "target_date", nullable = false)
  private LocalDate targetDate;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
  private List<AssetEntity> assets;

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
