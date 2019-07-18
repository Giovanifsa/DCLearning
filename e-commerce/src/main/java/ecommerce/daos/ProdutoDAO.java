package ecommerce.daos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;
import ecommerce.models.Produto;
import ecommerce.models.Loja;

@Named
public class ProdutoDAO implements Serializable {
	@Inject
	private EntityManager em;
	private static Object atualizarProdutoLock = new Object();
	
	/**
	 * Obtém os produtos adicionados recentemente ao banco de dados.
	 * @param quantidadeLimite Quantia limite de produtos à obter.
	 * @return Lista contendo os produtos recentes (pode ser menor que a quantidade)
	 */
	public List<Produto> buscarProdutosRecentes(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.id DESC", Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}
	
	public List<Produto> buscarMaisVendidos(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.sells DESC", Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}

	public List<Produto> listarProdutos() {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p", Produto.class).getResultList();
	}
	
	public boolean existeProdutos() {
		return !buscarProdutosRecentes(1).isEmpty();
	}
	
	@Transactional
	public void atualizarProduto(Produto p) {
		em.merge(p);
	}
	
	/**
	 * Utilize esse método para atualizar a quantia de vendas de um produto.
	 * Este método é thread-safe, portanto, ele aumenta as vendas de um produto
	 * garantindo que não haja erros.
	 */
	@Transactional
	public void produtoVendido(Produto p, int quantia) {
		synchronized (atualizarProdutoLock) {
			Produto dbp = em.find(Produto.class, p.getId());
			dbp.setVendas(dbp.getVendas() + quantia);
			
			em.merge(dbp);
		}
	}
	
	public void adicionarProduto(Produto p) {
		em.persist(p);
	}
	
	public Produto buscarProduto(int id) {
		return em.find(Produto.class, id);
	}

	public void removerProduto(Produto produto) {
		em.remove(em.merge(produto));
	}
	
	/*
	public void populaBanco() {
		System.out.println("Inicio");
		
		Produto p = new Produto();
		ResourceFile imagem = new ResourceFile();
		Loja loja = new Loja();
		
		loja.setCnpj("123456789123");
		loja.setFantasyName("Loja 00");
		
		imagem.setFileName("capita.png");
		
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
	}*/
}
