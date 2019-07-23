package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {
	private int produtoId;
	private Produto produto;
	
	private int quantidadeSelecionada = 1;
	
	@Inject
	private ProdutoDAO produtoDao;
	
	@Inject
	private CarrinhoBean carrinhoBean;
	
	@Inject
	private TemplateBean templateBean;
	
	public void carregarProduto() {
		produto = produtoDao.buscarProduto(produtoId);
	}
	
	public boolean produtoFoiCarregado() {
		return produto != null;
	}

	public int getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public String processarLookPainelImagem() {
		return produto.contemImagem() ? "success" : "danger";
	}

	public int getQuantidadeSelecionada() {
		return quantidadeSelecionada;
	}

	public void setQuantidadeSelecionada(int quantidadeSelecionada) {
		this.quantidadeSelecionada = quantidadeSelecionada;
	}
	
	public BigDecimal calcularPrecoQuantidade() {
		return produto.calcularPrecoFinal(quantidadeSelecionada);
	}
	
	public String adicionarAoCarrinho() {
		// Verificar se existe no estoque
		carrinhoBean.adicionarAoCarrinho(produto, quantidadeSelecionada);
		templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto " + produto.getNome() + "(" + quantidadeSelecionada + ") adicionado ao carrinho!", true);
		
		return "produto?faces-redirect=true&produtoId=" + produto.getId();
	}
}