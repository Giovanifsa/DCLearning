package ecommerce.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class ItemCarrinho {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantidade;
	
	@ManyToOne
	private Produto produto;
	
	public ItemCarrinho() {
		
	}
	
	public ItemCarrinho(Produto p, int quantidade) {
		this.quantidade = quantidade;
		this.produto = p;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal precoTotal() {
		return produto.calcularPrecoPelaQuantidade(quantidade);
	}
}
