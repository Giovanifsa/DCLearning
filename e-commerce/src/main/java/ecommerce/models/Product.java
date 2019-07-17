package ecommerce.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private long code;

	@NotNull
	private String name;

	@Lob
	private String description;
	private BigDecimal price;
	private BigDecimal profitMarginPercentual;
	
	@OneToOne(fetch = FetchType.EAGER)
	private ResourceFile prodImage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Store prodStore;
	
	private int sells = 0;
	
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
	
	public ResourceFile getProdImage() {
		return prodImage;
	}
	
	public void setProdImage(ResourceFile prodImage) {
		this.prodImage = prodImage;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Product && ((Product)obj).code == code);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSells() {
		return sells;
	}

	public void setSells(int sells) {
		this.sells = sells;
	}

	public Store getProdStore() {
		return prodStore;
	}

	public void setProdStore(Store prodStore) {
		this.prodStore = prodStore;
	}
}
