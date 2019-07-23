package ecommerce.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.servlet.http.Part;

import ecommerce.control.Transactional;
import ecommerce.daos.ArquivoDAO;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class NovoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();
	private Loja lojaDoProduto = new Loja();

	@Inject 
	private ProdutoDAO dao;
	@Inject
	private ArquivoDAO arquivoDAO;
	
	
	
	private Part imagem;
	
	private List<Produto> produtos = new ArrayList<>();
	
	//Esse método salva o novo produto
	@Transactional
	public String salvarProduto() throws IOException {
	
		if (this.produto.getId()==0) {
	
			/**
			 * Esse método salva a imagem no banco.
			 */
			ArquivoRecurso imagemDoProduto = dao.salvarImagemProduto(imagem);
			produto.setImagemProduto(imagemDoProduto);
		
			/**
			 * Esse método calcula o preço de venda
			 */			
			produto.calcularPrecoDeVenda(produto, lojaDoProduto);
			
			/**
			 * Esse salva o produto no banco
			 */
			
			dao.adicionarProduto(this.produto);
			System.out.println("Produto cadastrado com sucesso!");
			
		}else {
			/**
			 * Aqui atualiza o produto caso o mesmo já possui registro no banco.
			 */
			produto.calcularPrecoDeVenda(produto, lojaDoProduto);
			dao.atualizarProduto(this.produto);
			System.out.println("Produto atualizado com sucesso!");
		}
		
		this.produto = new Produto();
		
		return "/novoProduto?faces-redirect=true";
	}

	
	
	
	
	//Esse métodp atualiza o produto
	@Transactional
	public String atualiza(Produto produto) {
		dao.atualizarProduto(produto);
		
		return "/novoProduto?faces-redirect=true";
	}
	
	//Esse método busca todos os produtos cadastrado
	public List<Produto> getProdutos(){
		
		this.produtos = dao.listarProdutos();
		
		return produtos;
	}
	
	//Esse método deleta o produto
	@Transactional
	public String deletaProduto(Produto produto) {
		dao.removerProduto(produto);
		
		return "/novoProduto?faces-redirect=true";
	}
	
	public void atualizarProduto(Produto p) {
		produto = p;
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

	public ArquivoDAO getArquivoDAO() {
		return arquivoDAO;
	}

	public void setArquivoDAO(ArquivoDAO arquivoDAO) {
		this.arquivoDAO = arquivoDAO;
	}
	
	public Long getQuantidadeDisponivel(Produto produto) {
		String jpql = "select count(p) from Produto p where p.id = " + produto.getId();

		Query query = em.createQuery(jpql, Produto.class);
		Long total = (Long) query.getSingleResult();
		return total;
	}
	
}
