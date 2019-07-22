package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ecommerce.models._Produto;

@Named
public class _ProdutoDAO implements Serializable {
	@Inject
	private EntityManager em;
	
	@Inject
	private ArquivoDAO arquivoDao;
	
	private static Object atualizarProdutoLock = new Object();
	
	/**
	 * Obtém os produtos adicionados recentemente ao banco de dados.
	 * @param quantidadeLimite Quantia limite de produtos à obter.
	 * @return Lista contendo os produtos recentes (pode ser menor que a quantidade)
	 */
	public List<_Produto> buscarProdutosRecentes(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + _Produto.class.getSimpleName() + " p ORDER BY p.id DESC", _Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}
	
	public List<_Produto> buscarMaisVendidos(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + _Produto.class.getSimpleName() + " p ORDER BY p.sells DESC", _Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}

	public List<_Produto> listarProdutos() {
		return em.createQuery("select p from Produto p", _Produto.class).getResultList();
	}
	
	
	
	public void atualizarProduto(_Produto produto) {
		em.merge(produto);
	}
	
	/**
	 * Utilize esse método para atualizar a quantia de vendas de um produto.
	 * Este método é thread-safe, portanto, ele aumenta as vendas de um produto
	 * garantindo que não haja erros.
	 */

	public void produtoVendido(_Produto p, int quantia) {
		synchronized (atualizarProdutoLock) {
			_Produto dbp = em.find(_Produto.class, p.getId());
			dbp.setVendas(dbp.getVendas() + quantia);
			
			em.merge(dbp);
		}
	}
	
	
	public void adicionarProduto(_Produto p) {
		em.persist(p);
	}
	
	public _Produto buscarProduto(int id) {
		return em.find(_Produto.class, id);
	}

	public void removerProduto(_Produto produto) {
		em.remove(produto);
	}

	public Long quantidadeDisponivel(_Produto produto) {
		
		String jpql = "select p from Produto p where p.id = " + produto.getId();
		
		Query query = em.createQuery(jpql);
		Long total = (Long) query.getSingleResult();
		return total;
	}
	
}