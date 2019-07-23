package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.control.Transactional;
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
	SpinnerBean spinnerBean;
	
	@Inject
	DadosSessaoBean dadosSessao;
	
	
	/**
	 * Calcula o preço final (total) da soma do preço de todos os itens e suas quantidades.
	 * @return Valor total de compra.
	 */
	public BigDecimal calcularPrecoFinal() {
		BigDecimal price = new BigDecimal(0);
		
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			price = price.add(e.getProduto().getPrecoDeVenda().multiply(new BigDecimal(e.getQuantidade())));
		}
		
		return price;
	}
	
	public void adicionarAoCarrinho(Produto p, int quantidade) {
		//Encontra o mesmo produto no carrinho e soma o total com ele
		for (ItemCarrinho ic : dadosSessao.getProdutosCarrinho()) {
			if (ic.getProduto().equals(p)) {
				ic.setQuantidade(ic.getQuantidade() + quantidade);
				
				return;
			}
		}
		
		dadosSessao.getProdutosCarrinho().add(new ItemCarrinho(p, quantidade));
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
		return "loja?faces-redirect=true";	// RETORNAR A HOME
	}
	
	public String finalizarCompra() {
		return "finalizarCompra?faces-redirect=true";
	}

	public int qtdeDeUmItemNoCarrinho(Produto produto) {
			int total = dadosSessao.qtdeDeUmItemNoCarrinho(produto);
			return total;
	}
	
	public String adicionarViaSpinner(Produto produto) {
		
		Long estoque = (Long) produtoDao.getQuantidadeDisponivel(produto);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer quantidade = Integer.parseInt(spinnerBean.getValor());
		
		if(quantidade > estoque) { // Se não tem disponivel
			facesContext.getExternalContext().getFlash().setKeepMessages(true);
			facesContext.addMessage(null, new FacesMessage("Quantidade não disponível em estoque! Spin: " 
						+ spinnerBean.getValor()));
			
			return "carrinhoCompras?faces-redirect=true";
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage("messages", new FacesMessage("Atualizando ... " + spinnerBean.getValor()));
		
		ItemCarrinho item = new ItemCarrinho();
		item.setProduto(produto);
		item.setQuantidade(quantidade);

		dadosSessao.addUmItemAoCarrinho(item);
		
		return "carrinhoCompras?faces-redirect=true";
	}		
	
	
	public String removerViaSpinner(Produto produto) {

		dadosSessao.removerProduto(produto, Integer.parseInt(spinnerBean.getValor()));
		
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().getFlash().setKeepMessages(true);
		fc.addMessage("messages", new FacesMessage("Spinner: " + spinnerBean.getValor()));
//		item.setQuantidade(spinner.getValor());
//		dadosSessao.getProdutosCarrinho().add(item);
		
		return "carrinhoCompras?faces-redirect=true";
	}
	
	
	public int valorSpin(int val) {
		return val;
	}
	
	public String testarSpinner() {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().getFlash().setKeepMessages(true);
		fc.addMessage("messages", new FacesMessage("Spinner: " + spinnerBean.getValor()));
		return "teste?faces-redirect=true";
	}
}
