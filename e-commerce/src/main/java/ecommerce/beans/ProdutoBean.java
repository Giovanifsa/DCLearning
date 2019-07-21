package ecommerce.beans;

import java.io.Serializable;

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
	
	@Inject
	private ProdutoDAO produtoDao;
	
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
}
