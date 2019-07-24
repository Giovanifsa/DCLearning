package ecommerce.daos;

import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;
import ecommerce.servlets.ServletImagensProduto;

@Named
@RequestScoped
public class ProdutoDAO implements Serializable {
	@Inject
	private EntityManager em;
	
	@Inject
	private ArquivoDAO arquivoDao;
	
	@Inject
	private LojaDAO lojaDao;
	
	private static Object atualizarProdutoLock = new Object();

	/**
	 * Obtém os produtos adicionados recentemente ao banco de dados.
	 * 
	 * @param quantidadeLimite Quantia limite de produtos à obter.
	 * @return Lista contendo os produtos recentes (pode ser menor que a quantidade)
	 */
	public List<Produto> buscarProdutosRecentes(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.id DESC", Produto.class)
				.setMaxResults(quantidadeLimite).getResultList();
	}

	public List<Produto> buscarMaisVendidos(int quantidadeLimite) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p ORDER BY p.vendas DESC",
				Produto.class).setMaxResults(quantidadeLimite).getResultList();
	}

	public List<Produto> listarProdutos() {
		return em.createQuery("select p from " + Produto.class.getSimpleName() + " p", Produto.class).getResultList();
	}
	
	public List<Produto> listarProdutosUsuario(Usuario usuario) {
		return em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p WHERE p.lojaDoProduto.dono.id = :idDono", Produto.class)
				.setParameter("idDono", usuario)
				.getResultList();
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
		
		//Produto adicionado, devemos atualizar a loja agora
		//para aumentar o atributo de quantia de produtos dela.
		lojaDao.somarQuantiaProdutos(p.getLojaDoProduto(), 1);
	}

	public Produto buscarProduto(int id) {
		return em.find(Produto.class, id);
	}

	public void removerProduto(Produto produto) {
		em.remove(produto);
		lojaDao.somarQuantiaProdutos(produto.getLojaDoProduto(), -1);
	}
	
	public void removerProdutosDaLoja(Loja loja) {
		em.createQuery("DELETE " + Produto.class.getSimpleName() + " WHERE lojaDoProduto.id = :idLoja")
			.setParameter("idLoja", loja.getId())
			.executeUpdate();
		
		loja.setQuantiaProdutos(0);
		lojaDao.atualizarLoja(loja);
	}
	
	public List<Produto> procurarPorConteudoNome(String nomePesquisa) {
		TypedQuery<Produto> query = em.createQuery("SELECT p FROM " + Produto.class.getSimpleName() + " p WHERE p.nome LIKE :nomePesquisa", Produto.class);
		query.setParameter("nomePesquisa", nomePesquisa);
		
		return query.getResultList();
	}

//	public Long getQuantidadeDisponivel(Produto produto) {
//
//		/**
//		 * Esse método retora a quantidade de determinado no estoque.
//		 * 
//		 * @return Long
//		 * @param produto
//		 */
//
//		String jpql = "select count(p) from Produto p where p.id = " + produto.getId();
//
//		Query query = em.createQuery(jpql, Produto.class);
//		Long total = (Long) query.getSingleResult();
//		return total;
//	}
	
	public ArquivoRecurso salvarImagemProduto(Part imagem) throws IOException {
		//Verifica se o arquivo é realmente uma imagem.
		if (!imagem.getContentType().startsWith("image/")) {
			throw new InputMismatchException("O arquivo enviado pelo usuário como imagem de produto não é uma imagem!");
		}
		
		return arquivoDao.salvarArquivo(imagem.getSubmittedFileName(), ServletImagensProduto.DIRETORIO_IMAGENS_PRODUTOS, imagem.getInputStream().readAllBytes());
	}
	
	
	
}