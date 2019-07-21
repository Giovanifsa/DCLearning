package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;

import ecommerce.control.Transactional;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Spinner;
import ecommerce.tools.MecanismoDeHash;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CarrinhoBean implements Serializable {
	@Inject
	private ProdutoDAO produtoDao;
	
	@Inject
	DadosSessaoBean dadosSessao;
	
	@Inject
	private MecanismoDeHash hashing;
	
	private ArrayList<ItemCarrinho> produtosCarrinho = new ArrayList<>();
	
	public BigDecimal calcularPrecoFinal() {
		BigDecimal price = new BigDecimal(0);
		
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			price = price.add(e.getProduto().getPreco().multiply(new BigDecimal(e.getQuantidade())));
		}
		
		return price;
	}
	
	/**
	 * Altera a quantidade de um item no carrinho
	 * @param p Produto cuja quantidade será alterada.
	 * @param quantidade Nova quantidade de itens.
	 */
	@Deprecated
	public void setQuantidadeItem(Produto p, int quantidade) {
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			if (e.getProduto().equals(p)) {
				if (quantidade == 0) {
					dadosSessao.getProdutosCarrinho().remove(e);
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
	
	/**
	 * Calcula a quantidade total de itens no carrinho,
	 * isto é, soma total da quantidade de todos os produtos do carrinho.
	 * @return Quantidade total dos produtos do carrinho.
	 */
	public int getQuantidadeItens() {
		int quantidade =0;
		
		for (ItemCarrinho e : produtosCarrinho) {
			quantidade += e.getQuantidade();
		}
		
		return quantidade;
	}
	
	public List<Produto> produtosCarrinho() {
		return produtoDao.listarProdutos();
	}
	
	@Transactional
	public String removeProduto(Produto produto) {
		
		this.produtoDao.removerProduto(produto);
		
		return "carrinhoCompras?faces-redirect=true";
	}

	public int selecionarQuantidade() {
		
		int quantidade = 1;
		if(quantidade == 1) {
			return quantidade;
		}
		return 0;
	}
	
	public String continuarComprando() {
		return "novaLoja?faces-redirect=true";	//	DEVE RETORNAR A HOME
	}
	
	public String finalizarCompra() {
		return "finalizarCompra?faces-redirect=true";
	}
	
}
