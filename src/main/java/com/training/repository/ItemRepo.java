package com.training.repository;

import org.springframework.data.repository.CrudRepository;
import com.training.model.Item;

public interface ItemRepo extends CrudRepository<Item, Integer> {

}