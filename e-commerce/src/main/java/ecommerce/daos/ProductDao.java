package ecommerce.daos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;
import ecommerce.models.BinaryData;
import ecommerce.models.Product;
import ecommerce.models.ResourceFile;
import ecommerce.models.Store;

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

	public List<Product> listarProdutos(){
		return em.createQuery("select p from Product p", Product.class).getResultList();
	}
	
	@Transactional
	public synchronized void updateProduct(Product p) {
		em.merge(p);
	}
	
	public ResourceFile getProductImage(int productId) {
		return em.find(Product.class, productId).getProdImage();
	}
	
	@Transactional
	public void addProduct(Product p) {
		em.persist(p);
	}
	
	public Product getProduct(int id) {
		return em.find(Product.class, id);
	}

	public void remover(Product produto) {
		em.remove(em.merge(produto));
	}
	
	public void populaBanco() {
		System.out.println("Inicio");
		
		Product p = new Product();
		ResourceFile imagem = new ResourceFile();
		Store loja = new Store();
		
		loja.setCnpj("123456789123");
		loja.setFantasyName("Loja 00");
		
		imagem.setFileName("capita.png");
		imagem.setFilePath("resource/images/");
		
		p.setCode(123456);
		p.setDescription("Filme da Capitã Marvel");
		p.setName("Capitã Marvel");
		p.setPrice(new BigDecimal(9.5));
		p.setProdImage(imagem);
		p.setProfitMarginPercentual(new BigDecimal(2.20));
		p.setProdStore(loja);
		p.setSells(550);
		
		System.out.println("Fim");
		em.persist(p);
	}
}
