package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;
import ecommerce.tools.MecanismoDeHash;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class CarrinhoBean implements Serializable {
	@Inject
	private ProdutoDAO produtoDao;
	
	@Inject
	private MecanismoDeHash hashing;
	
	private ArrayList<ItemCarrinho> produtosCarrinho = new ArrayList<>();
	
	public String removerProduto(ItemCarrinho cartProduct) {
		for (ItemCarrinho e : produtosCarrinho) {
			if (e.equals(cartProduct)) {
				produtosCarrinho.remove(e);
				
				break;
			}
		}
		
		return "cart?faces-redirect=true";
	}
	
	public BigDecimal calcularPrecoFinal() {
		BigDecimal price = new BigDecimal(0);
		
		for (ItemCarrinho e : produtosCarrinho) {
			price = price.add(e.getProduto().getPreco().multiply(new BigDecimal(e.getQuantidade())));
		}
		
		return price;
	}
	
	public void setQuantidadeItem(Produto p, int quantidade) {
		for (ItemCarrinho e : produtosCarrinho) {
			if (e.getProduto().equals(p)) {
				if (quantidade == 0) {
					produtosCarrinho.remove(e);
				}
				
				else if (quantidade < 0) {
					FacesContext.getCurrentInstance().addMessage("shoppingCartMessage", new FacesMessage("É necessário um valor acima ou igual a zero."));
				}
				
				else {
					e.setQuantidade(quantidade);
				}
				
				break;
			}
		}
	}
	
	public int getQuantidadeItens() {
		int quantidade = 0;
		
		for (ItemCarrinho e : produtosCarrinho) {
			quantidade += e.getQuantidade();
		}
		
		return quantidade;
	}
	
	public List<ItemCarrinho> getProdutosCarrinho() {
		return produtosCarrinho;
	}
}
