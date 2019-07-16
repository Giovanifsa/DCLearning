package ecommerce.models;

import javax.persistence.Id;

public class Product {
	@Id
	private long code;
	
	private String name;
	private String description;
	private double price;
	private double profitMarginPercentual;
	
	private BinaryData prodImage;
	private Store prodStore;
	
	public long getCode() {
		return code;
	}
	
	public void setCode(long code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getProfitMarginPercentual() {
		return profitMarginPercentual;
	}
	
	public void setProfitMarginPercentual(double profitMarginPercentual) {
		this.profitMarginPercentual = profitMarginPercentual;
	}
	
	public BinaryData getProdImage() {
		return prodImage;
	}
	
	public void setProdImage(BinaryData prodImage) {
		this.prodImage = prodImage;
	}
	
	public Store getProdStore() {
		return prodStore;
	}

	public void setProdStore(Store prodStore) {
		this.prodStore = prodStore;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Product && ((Product)obj).code == code);
	}
}
