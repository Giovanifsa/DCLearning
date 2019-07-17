package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ecommerce.models.Product;

public class ProductDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	public List<Product> listarProdutos(){
		
		String jpql = "select p from Product p";
		return em.createQuery(jpql, Product.class).getResultList();
	}
	
	public void remover(Product produto) {
		int code = (int) produto.getCode();
		String jpql ="delete from Product p where p.code = :pCode";
//		em.remove(em.merge(produto));
		em.createQuery(jpql).setParameter("pCode", code);
		
	}
	
	
}
