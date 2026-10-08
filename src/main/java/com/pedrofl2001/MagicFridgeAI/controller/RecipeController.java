package com.pedrofl2001.MagicFridgeAI.controller;

import com.pedrofl2001.MagicFridgeAI.model.FoodItem;
import com.pedrofl2001.MagicFridgeAI.service.FoodItemService;
import com.pedrofl2001.MagicFridgeAI.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final FoodItemService foodItemService;
    private final GeminiService geminiService;

    public RecipeController(FoodItemService foodItemService, GeminiService geminiService) {
        this.foodItemService = foodItemService;
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {

        List<FoodItem> foodItems = foodItemService.list();

        return geminiService.generateRecipe(foodItems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }


}

