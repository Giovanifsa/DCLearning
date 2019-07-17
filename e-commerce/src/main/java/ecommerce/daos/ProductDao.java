package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;
import ecommerce.models.BinaryData;
import ecommerce.models.Product;

@Named
public class ProductDao implements Serializable {
	@Inject
	private EntityManager em;
	
	public List<Product> getLatestProducts(int quantity) {
		return em.createQuery("SELECT p FROM " + Product.class.getSimpleName() + " p ORDER BY p.id DESC", Product.class).setMaxResults(quantity).getResultList();
	}
	
	public List<Product> getBestSelling(int quantity) {
		return em.createQuery("SELECT p FROM " + Product.class.getSimpleName() + " p ORDER BY p.sells DESC", Product.class).setMaxResults(quantity).getResultList();
	}
	
	@Transactional
	public synchronized void updateProduct(Product p) {
		em.merge(p);
	}
	
	public BinaryData getProductImage(int productId) {
		return em.find(Product.class, productId).getProdImage();
	}
	
	@Transactional
	public void addProduct(Product p) {
		em.persist(p);
	}
	
	public Product getProduct(int id) {
		return em.find(Product.class, id);
	}
}
