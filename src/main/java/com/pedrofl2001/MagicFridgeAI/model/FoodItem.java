package com.pedrofl2001.MagicFridgeAI.model;

import com.pedrofl2001.MagicFridgeAI.enums.FoodCategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_item")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    @Enumerated(EnumType.STRING)
    private FoodCategoryEnum category;
    private Integer quantity;

    private LocalDateTime dueDate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(FoodCategoryEnum category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
