package com.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/{orderID}")
	public ResponseEntity<Object> placeOrder(@PathVariable("orderID") int orderID) {
		Optional<Orders> order = orderRepo.findById(orderID);
		ResponseEntity<Object> apiResponse;

		if(order.isPresent()) {
			Orders orderObj = order.get();
			String orderObjStatus = orderObj.getOrderStatus();

			if(orderObjStatus == "PENDING") {
				
				// Update order status in order table
				Orders updatedOrder = new Orders(orderObj.getOrderID(), orderObj.getCartID(), "PROCESSED");
//				updatedOrder.setOrderID(orderObj.getOrderID());
//				updatedOrder.setCartID(orderObj.getCartID());
//				updatedOrder.setOrderStatus("PROCESSED");
				orderRepo.save(updatedOrder);
				apiResponse = ResponseEntity.ok().body(updatedOrder);
				
				// Reduce item quantity in item table by 1
				Cart currOrderCart = cartRepo.findById(orderObj.getCartID()).get();
				Item currOrderItem = itemRepo.findById(currOrderCart.getItemID()).get();

				Item updatedItem = new Item(currOrderItem.getItemID(), currOrderItem.getName(), currOrderItem.getPrice(), currOrderItem.getQuantity() - 1);
				itemRepo.save(updatedItem);
				
			} else {
				apiResponse = ResponseEntity.badRequest().body("Order already processed");
			}
		} else {
			apiResponse = ResponseEntity.notFound().build();
		}
		
		return apiResponse;
	}
}
