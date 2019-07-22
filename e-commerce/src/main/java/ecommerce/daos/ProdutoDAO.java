package ecommerce.daos;

import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;
import javax.persistence.TypedQuery;

import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Produto;
import ecommerce.servlets.ServletImagensProduto;

@Named
public class ProdutoDAO implements Serializable {
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
	public List<Produto> buscarProdutosRecentes(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.id DESC", Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}
	
	public List<Produto> buscarMaisVendidos(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.sells DESC", Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}

	public List<Produto> listarProdutos() {
		/**
		 * Retorna uma lista de todos os produtos na tabela de produtos,
		 * depois de implementado a tela de vendas, deve retornar os produtos
		 * adicionados ao carrinho, para preencher a tela do carrinho
		 */
		return em.createQuery("select p from Produto p", Produto.class).getResultList();
	}
	
	public void atualizarProduto(Produto p) {
		em.merge(p);
	}
	
	/**
	 * Utilize esse método para atualizar a quantia de vendas de um produto.
	 * Este método é thread-safe, portanto, ele aumenta as vendas de um produto
	 * utilizando um semáforo, garantindo que não haja erros.
	 */
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
		em.remove(produto);
	}
	
	public List<Produto> procurarPorConteudoNome(String nomePesquisa) {
		TypedQuery<Produto> query = em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p WHERE p.nome LIKE :nomePesquisa", Produto.class);
		query.setParameter("nomePesquisa", nomePesquisa);
		
		return query.getResultList();
	}

	public Long quantidadeDisponivel(Produto produto) {
		
		/**
		 * Retorna a quantidade de um produto na tabela produtos.
		 * No momento propício, deve retornar a quantidade de produtos
		 * adicionados ao carrinho, através da tela de vendas.
		 */
		
		String jpql = "select count(p) from Produto p";
		
		@SuppressWarnings("unchecked")
		TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery(jpql);
		
		return query.getSingleResult();
	}
	
	public ArquivoRecurso salvarImagemProduto(Part imagem) throws IOException {
		//Verifica se o arquivo é realmente uma imagem.
		if (!imagem.getContentType().startsWith("image/")) {
			throw new InputMismatchException("O arquivo enviado pelo usuário como imagem de produto não é uma imagem!");
		}
		
		return arquivoDao.salvarArquivo(imagem.getSubmittedFileName(), ServletImagensProduto.DIRETORIO_IMAGENS_PRODUTOS, imagem.getInputStream().readAllBytes());
	}
}