package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import ecommerce.control.Transactional;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class NovoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();

	@Inject 
	private ProdutoDAO dao;
	
	private Part imagem;
	
	private List<Produto> produtos = new ArrayList<>();
	
	//Esse método salva o novo produto
	@Transactional
	public String salvarProduto() {
	
		if (this.produto.getId()==0) {
			dao.adicionarProduto(this.produto);
			System.out.println("Produto cadastrado com sucesso!");
		}else {
			dao.atualizarProduto(this.produto);
			System.out.println("Produto atualizado com sucesso!");
		}
		
		this.produto = new Produto();
		
		return "/NovoProduto?faces-redirect=true";
	}
	
	//Esse método busca todos os produtos cadastrado
	public List<Produto> getProdutos(){
		
		this.produtos = dao.listarProdutos();
		
		return produtos;
	}
	
	//Esse método deleta o produto
	@Transactional
	public String deletaProduto() {
		dao.removerProduto(produto);
		
		return "/NovoProduto?faces-redirect=true";
	}

	//getters and setters
	
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
	
	public Long getQuantidadeDisponivel(Produto produto) {
		return dao.getQuantidadeDisponivel(produto);
	}
	
}
