package ecommerce.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
	@Id
	private long code;

	@NotNull
	private String name;

	@Lob
	private String description;
	private BigDecimal price;
	private BigDecimal profitMarginPercentual;
	
	@OneToOne
	private BinaryData prodImage;
	
	@ManyToOne
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
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getProfitMarginPercentual() {
		return profitMarginPercentual;
	}
	
	public void setProfitMarginPercentual(BigDecimal profitMarginPercentual) {
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
