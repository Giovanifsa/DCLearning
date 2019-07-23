package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.tools.MecanismoDeHash;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CarrinhoBean implements Serializable {
	@Inject
	private ProdutoDAO produtoDao;
	
	@Inject
	private MecanismoDeHash hashing;

	@Inject
	private DadosSessaoBean dadosSessao;
	
	@Inject
	private TemplateBean templeteBean;
	
	@Inject
	private LoginBean loginBean;
	
	private int spinner = 1;
	private int produtoAtualizado;

	/**
	 * Calcula o preço final (total) da soma do preço de todos os itens e suas quantidades.
	 * @return Valor total de compra.
	 */
	
	public int getSpinner() {
		return spinner;
	}
	
	public void setSpinner(int spinner) {
		this.spinner = spinner;
	}
	public int getProdutoAtualizado() {
		return produtoAtualizado;
	}
	public void setMax(int produtoAtualizado) {
		this.produtoAtualizado = produtoAtualizado;
	}
	
	public BigDecimal calcularPrecoFinal() {
		BigDecimal price = new BigDecimal(0);
		
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			price = price.add(e.getProduto().calcularPrecoPelaQuantidade((e.getQuantidade())));
		}
		
		return price;
	}
	
	public void adicionarAoCarrinho(Produto p, BigDecimal quantidade) {
		//Encontra o mesmo produto no carrinho e soma o total com ele
		for (ItemCarrinho ic : dadosSessao.getProdutosCarrinho()) {
			if (ic.getProduto().equals(p)) {
				ic.setQuantidade(ic.getQuantidade() + 5);
				
				return;
			}
		}
		
		dadosSessao.getProdutosCarrinho().add(new ItemCarrinho(p, 5));
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
	
	public List<ItemCarrinho> produtosCarrinho() {
		return dadosSessao.getProdutosCarrinho();
	}
	
	public void removerProduto(Produto produto) {
		dadosSessao.removerProduto(produto);
	}

	public int selecionarQuantidade() {
		
		int quantidade = 0;
		if(quantidade == 1) {
			return quantidade;
		}
		return 0;	
	}
	
	public String voltarParaLoja() {
		return "loja?faces-redirect=true";
	}
	
	public String finalizarCompra() {
		
		if(!loginBean.usuarioEstaLogado()) {
			templeteBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, 
					"Para finalizar a sua compra, é necessário fazer o Login!", true);
			return "login?faces-redirect=true";
		}
		return "";
	}
	
	public void atualizarProdutoCarrinho(Produto produto) {
		
//	
		
	}

}