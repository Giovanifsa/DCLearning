package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ecommerce.models.Loja;

@SuppressWarnings("serial")
public class LojaDAO implements Serializable {
	@Inject
	private EntityManager manager;

	public Loja buscarLojaPorCnpj(int cnpj) {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l where l.cnpj = :cnpj", Loja.class).setParameter("cnpj", cnpj).getSingleResult();
	}
	
	public List<Loja> listarLojas() {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l", Loja.class).getResultList();
	}
	
	public void removerLoja(Loja loja){
		manager.remove(loja);
	}
	
	public void atualizarLoja(Loja loja) {
		manager.merge(loja);
	}

	public void adicionarLoja(Loja loja) {
		manager.persist(loja);
	}
}
