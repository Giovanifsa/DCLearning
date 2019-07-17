package ecommerce.models;

import java.math.BigDecimal;

public class CartProduct {
	private int quantity;
	private Product product;
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public CartProduct(Product product, int quantity) {
		this.quantity = quantity;
		this.product = product;
	}
	
	public BigDecimal getPrice() {
		return (product.getPrice().multiply(new BigDecimal(quantity)));
	}
}
