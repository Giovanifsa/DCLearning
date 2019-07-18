package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;
import ecommerce.control.Transactional;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@RequestScoped
public class NovoProdutoBean implements Serializable {
	private Produto produto = new Produto();
	@Inject
	EntityManager em;

	@Inject
	private ProdutoDAO dao;

	private Part imagem;

	@Transactional
	public String salvarProduto() {
		dao.adicionarProduto(produto);
		String novoProduto = produto.toString();
		System.out.println("Produto salvo com sucesse: " + novoProduto);
		
		Produto produto = new Produto();

		return "/addprod?faces-redirect=true";

	}
	
	
	public BigDecimal calcularPrecoVenda() {
		BigDecimal precoVenda = this.produto.getCusto().
				multiply((new BigDecimal(1).add(produto.getMargemDeLucroPorcentual().divide(new BigDecimal(100)))));
		 this.produto.setPreco(precoVenda);
		
		 return precoVenda;
		
	}
	

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Part getImagem() {
		return imagem;
	}

	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}

}
