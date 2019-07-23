package ecommerce.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany
	private List<ItemCarrinho> itensVendidos;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp timestampVenda;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemCarrinho> getItensVendidos() {
		return itensVendidos;
	}

	public void setItensVendidos(List<ItemCarrinho> itensVendidos) {
		this.itensVendidos = itensVendidos;
	}
}
