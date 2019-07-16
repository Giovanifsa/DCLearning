package ecommerce.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Store implements Serializable {
	@Id
	private String cnpj;
	
	private String fantasyName;
	private double totalExpenses;
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getFantasyName() {
		return fantasyName;
	}
	
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}
	
	public double getTotalExpenses() {
		return totalExpenses;
	}
	
	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
}
