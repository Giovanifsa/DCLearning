package ecommerce.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.primefaces.model.UploadedFile;

import ecommerce.control.Transactional;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class NovoProdutoBean implements Serializable {
	private Produto produto = new Produto();

	@Inject 
	private ProdutoDAO dao;
	
	private Part imagem;
	
	@Transactional
	public String salvarProduto() {
		dao.adicionarProduto(produto);
		System.out.println(produto.toString());
		
		return "/addprod?faces-redirect=true";
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
