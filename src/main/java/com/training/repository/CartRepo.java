package com.training.repository;

import org.springframework.data.repository.CrudRepository;
import com.training.model.Cart;

public interface CartRepo extends CrudRepository<Cart, Integer> {
	
}
