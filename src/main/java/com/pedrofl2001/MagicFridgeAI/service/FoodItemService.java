package com.pedrofl2001.MagicFridgeAI.service;

import com.pedrofl2001.MagicFridgeAI.model.FoodItem;
import com.pedrofl2001.MagicFridgeAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItem save(FoodItem foodItem){
        return repository.save(foodItem);
    }

    public List<FoodItem> list(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Optional<FoodItem> findById(Long id) {
        return repository.findById(id);
    }

    public FoodItem update(FoodItem foodItem) {
        return repository.save(foodItem);
    }
}
