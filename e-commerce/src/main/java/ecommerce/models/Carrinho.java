package ecommerce.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {

	private List<ItemCarrinho> itens = new ArrayList<>();
	
	private BigDecimal total;
	
	public Carrinho() {
	}

	public List<ItemCarrinho> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinho> itens) {
		this.itens = itens;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
