package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProductDao;
import ecommerce.models.Product;

@Named
@ViewScoped
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Product> produtos = new ArrayList<>();
	
	@Inject
	ProductDao produtoDao;
	
	public List<Product> listarProdutos() {
		this.produtos = produtoDao.listarProdutos();
		return produtos;
	}
	
	public void remover(Product produto) {
		System.out.println("Removendo o produto: " + produto);
		produtoDao.remover(produto);
	}
	
}
