package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import ecommerce.control.Transactional;
import ecommerce.daos.ArquivoDAO;
import ecommerce.daos._ProdutoDAO;
import ecommerce.models._Produto;

@Named
@ViewScoped
public class _NovoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private _Produto produto = new _Produto();

	@Inject 
	private _ProdutoDAO dao;
	@Inject
	private ArquivoDAO arquivoDAO;
	
	private Part imagem;
	
	private List<_Produto> produtos = new ArrayList<>();
	
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
		
		this.produto = new _Produto();
		
		return "/novoProduto?faces-redirect=true";
	}
	
	//Esse métodp atualiza o produto
	@Transactional
	public String atualiza(_Produto produto) {
		dao.atualizarProduto(produto);
		
		return "/novoProduto?faces-redirect=true";
	}
	
	//Esse método busca todos os produtos cadastrado
	public List<_Produto> getProdutos(){
		
		this.produtos = dao.listarProdutos();
		
		return produtos;
	}
	
	//Esse método deleta o produto
	@Transactional
	public String deletaProduto(_Produto produto) {
		dao.removerProduto(produto);
		
		return "/novoProduto?faces-redirect=true";
	}
	
	public void atualizarProduto(_Produto p) {
		produto = p;
	}

	//getters and setters
	
	public _Produto getProduto() {
		return produto;
	}

	public void setProduto(_Produto produto) {
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
	
}
