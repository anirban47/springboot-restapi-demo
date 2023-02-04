package com.training.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Item;
import com.training.repository.ItemRepo;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	@Autowired
	ItemRepo itemRepo;

	@GetMapping
	public List<Item> findAllItems() {
		return (List<Item>) itemRepo.findAll();
	}

	@PostMapping
	public Item addItem(@RequestBody Item item) {
		return itemRepo.save(item);
	}
	
	@GetMapping("/{itemID}") 
	public ResponseEntity<Item> findItem(@PathVariable("itemID") int itemID) {
	    Optional<Item> item = itemRepo.findById(itemID);
	    
	    if(item.isPresent()) {
	        return ResponseEntity.ok().body(item.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
