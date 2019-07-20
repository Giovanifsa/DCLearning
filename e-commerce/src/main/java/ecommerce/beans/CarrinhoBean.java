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
@SessionScoped
public class CarrinhoBean implements Serializable {
	@Inject
	private ProdutoDAO produtoDao;
	
	@Inject
	private MecanismoDeHash hashing;

	@Inject
	Spinner spinner;
	
	@Inject
	private DadosSessaoBean dadosSessao;
	
	/**
	 * Calcula o preço final (total) da soma do preço de todos os itens e suas quantidades.
	 * @return Valor total de compra.
	 */
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
		int quantidade = 0;
		
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
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
		
		int quantidade = 0;
		if(quantidade == 1) {
			return quantidade;
		}
		return 0;
	}
	
	public String continuarComprando() {
		return "novaLoja?faces-redirect=true";	// RETORNAR A HOME
	}
	
	public String finalizarCompra() {
		return "finalizarCompra?faces-redirect=true";
	}

	public String atualizarQuantidade(Produto produto) {
//	POR ENQUANTO FAZ UM COUNT NO BANCO, TEM QUE MUDAR PRA COUNT NO ESTOQUE		
		Long estoque = produtoDao.quantidadeDisponivel(produto);
		
		if(spinner.getValor() > estoque) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Quantidade indisponível!"));
//	TESTAR ESSA MENSAGEM QUANDO TIVER A TELA DE PRODUTOS			
		}
		
		ItemCarrinho item = new ItemCarrinho();

		item.setProduto(produto);
		item.setQuantidade(spinner.getValor());
		produtosCarrinho.add(item);
		
		return "carrinhoCompras?faces-redirect=true";
	}
	
}
