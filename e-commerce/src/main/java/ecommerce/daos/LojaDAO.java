package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ecommerce.models.Loja;
import ecommerce.models.Usuario;

@SuppressWarnings("serial")
@RequestScoped
public class LojaDAO implements Serializable {
	@Inject
	private EntityManager manager;
	
	@Inject
	private ProdutoDAO produtoDao;
	
	private Object quantiaProdutosLock = new Object();

	public Loja buscarLojaPorCnpj(int cnpj) {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l where l.cnpj = :cnpj", Loja.class).setParameter("cnpj", cnpj).getSingleResult();
	}
	
	public List<Loja> listarLojas() {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l", Loja.class).getResultList();
	}
	
	public List<Loja> listarLojasUsuario(Usuario usuario) {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l WHERE l.dono.id = :idUsuario", Loja.class)
				.setParameter("idUsuario", usuario.getId())
				.getResultList();
	}
	
	public Loja getLoja(int id) {
		return manager.find(Loja.class, id);
	}
	
	public void removerLoja(Loja loja){
		//Para remover a loja, devemos remover seus produtos.
		produtoDao.removerProdutosDaLoja(loja);
		manager.remove(loja);
	}
	
	public void atualizarLoja(Loja loja) {
		manager.merge(loja);
	}

	public void adicionarLoja(Loja loja) {
		manager.persist(loja);
	}
	
	public void somarQuantiaProdutos(Loja l, int soma) {
		synchronized (quantiaProdutosLock) {
			Loja loja = manager.find(Loja.class, l.getId());
			loja.setQuantiaProdutos(loja.getQuantiaProdutos() + soma);
			
			atualizarLoja(loja);
		}
	}
	
	public long contarProdutos(Loja loja) {
		return manager.find(Loja.class, loja.getId()).getQuantiaProdutos();
	}
}
