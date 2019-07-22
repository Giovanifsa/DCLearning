package ecommerce.models;

import java.math.BigDecimal;

public class ItemCarrinho {
	private int quantidade;
	private Produto produto;
	
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
		
		return produto.getPrecoDeVenda().multiply(new BigDecimal(quantidade));
	}
}
