package ecommerce.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import ecommerce.daos.ProductDao;
import ecommerce.models.Product;

@Model
public class ProdutoBean {

	@Inject
	private ProductDao produtoDao;
	
	public List<Product> listarProdutos(){
		return produtoDao.listarProdutos();
	}
	
	public void removerProduto(Product p) {
		produtoDao.remover(p);
	}
}
