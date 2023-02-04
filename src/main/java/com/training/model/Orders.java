package com.training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int orderID;
	private int cartID;
	private String orderStatus;

	public Orders() {
	}
	
	public Orders(int orderID, int cartID, String orderStatus) {
		setOrderID(orderID);
		setCartID(cartID);
		setOrderStatus(orderStatus);
	}
	
	public Orders(int cartID, String orderStatus) {
		setCartID(cartID);
		setOrderStatus(orderStatus);
	}
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getCartID() {
		return cartID;
	}
	public void setCartID(int cartID) {
		this.cartID = cartID;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
