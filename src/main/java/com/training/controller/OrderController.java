package com.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Cart;
import com.training.model.Item;
import com.training.model.Orders;
import com.training.repository.CartRepo;
import com.training.repository.ItemRepo;
import com.training.repository.OrderRepo;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderRepo orderRepo;
	@Autowired
	ItemRepo itemRepo;
	@Autowired
	CartRepo cartRepo;
	
	@GetMapping
	public List<Orders> findAllOrders() {
		return (List<Orders>) orderRepo.findAll();
	}

	@GetMapping("/{orderID}")
	public ResponseEntity<Object> findOrder(@PathVariable("orderID") int orderID) {
	    Optional<Orders> order = orderRepo.findById(orderID);
	    if(order.isPresent()) {
	        return ResponseEntity.ok().body(order.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping
	public Orders addOrder(@RequestBody Map<String, Object> cartMap) {
		int cartID = (int) cartMap.get("cartID");
		Orders newOrder = new Orders(cartID, "PENDING");
		return orderRepo.save(newOrder);
	}
	
	@PatchMapping("/{orderID}")
	public ResponseEntity<Object> placeOrder(@PathVariable("orderID") int orderID) {
		Optional<Orders> order = orderRepo.findById(orderID);
		ResponseEntity<Object> apiResponse;

		if(order.isPresent()) {
			Orders orderObj = order.get();
			String orderObjStatus = orderObj.getOrderStatus();
			System.out.println(orderObjStatus);
			if(orderObjStatus.equals("PENDING")) {
				// Update order status in order table
				orderObj.setOrderStatus("PROCESSED");
				orderRepo.save(orderObj);
				apiResponse = ResponseEntity.ok().body(orderObj);
				
				// Reduce item quantity in item table by quantity in cart
				Cart currOrderCartObj = cartRepo.findById(orderObj.getCartID()).get();
				int itemQuantityInCart = currOrderCartObj.getQuantity();
				Item currOrderItemObj = itemRepo.findById(currOrderCartObj.getItemID()).get();

				int prevItemQuantity = currOrderItemObj.getQuantity();
				currOrderItemObj.setQuantity(prevItemQuantity - itemQuantityInCart);
				itemRepo.save(currOrderItemObj);
				
			} else {
				apiResponse = ResponseEntity.badRequest().body("Order already processed");
			}
		} else {
			apiResponse = ResponseEntity.notFound().build();
		}
		
		return apiResponse;
	}
}
