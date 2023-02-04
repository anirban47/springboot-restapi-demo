package com.training.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.training.model.Cart;
import com.training.repository.CartRepo;

@RestController
@RequestMapping("/api/carts")
public class CartController {
	@Autowired
	CartRepo cartRepo;
	
	@GetMapping
	public List<Cart> findAllCart() {
		return (List<Cart>) cartRepo.findAll();
	}
	
    @PostMapping
    public ResponseEntity<Object> addToCart(@RequestBody Cart cart) {
        try {
        	Cart result = cartRepo.save(cart);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
	@GetMapping("/{cartID}")
	public ResponseEntity<Object> findCart(@PathVariable("cartID") int cartID) {
		Optional<Cart> cart = cartRepo.findById(cartID);
	    if(cart.isPresent()) {
	        return ResponseEntity.ok().body(cart.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}

