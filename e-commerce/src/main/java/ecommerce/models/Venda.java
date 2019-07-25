package ecommerce.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String timestampVenda;
	@ManyToOne
	private Usuario cliente;
	private BigDecimal valor;
	
	@OneToMany
	private List<ItemCarrinho> produtosComprados;
	
	public String getTimestampVenda() {
		return timestampVenda;
	}
	
	public List<ItemCarrinho> getProdutosComprados() {
		return produtosComprados;
	}
	public void setProdutosComprados(List<ItemCarrinho> produtosComprados) {
		this.produtosComprados = produtosComprados;
	}
	public void setTimestampVenda(String timestampVenda) {
		this.timestampVenda = timestampVenda;
	}
	
	public Usuario getCliente() {
		return cliente;
	}
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	 public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
