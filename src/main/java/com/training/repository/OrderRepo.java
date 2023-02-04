package com.training.repository;

import org.springframework.data.repository.CrudRepository;
import com.training.model.Orders;

public interface OrderRepo extends CrudRepository<Orders, Integer> {

}