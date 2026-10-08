package com.pedrofl2001.MagicFridgeAI.controller;

import com.pedrofl2001.MagicFridgeAI.model.FoodItem;
import com.pedrofl2001.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService foodItemService;

    public  FoodItemController(FoodItemService foodItemService){
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public ResponseEntity<FoodItem> create(@RequestBody FoodItem foodItem){
        FoodItem result = foodItemService.save(foodItem);
        return  ResponseEntity.ok(result);
    }

    @GetMapping
    public  ResponseEntity<List<FoodItem>> listAll(){
        List<FoodItem> result = foodItemService.list();
        return  ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> update(@PathVariable Long id, @RequestBody FoodItem foodItem){
        return  foodItemService.findById(id)
                .map(existFoodItem -> {
                    foodItem.setId(existFoodItem.getId());
                    FoodItem result = foodItemService.save(foodItem);
                    return ResponseEntity.ok(result);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FoodItem>> findById(@PathVariable Long id){
        Optional<FoodItem> result = foodItemService.findById(id);
        return  ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        foodItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
