package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;
import ecommerce.models.Loja;

@SuppressWarnings("serial")
public class LojaDAO implements Serializable {
	@Inject
	private EntityManager manager;

	@Transactional
	public Loja buscarLojaPorCnpj(int cnpj) {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l where l.cnpj = :cnpj", Loja.class).setParameter("cnpj", cnpj).getSingleResult();
	}
	
	@Transactional
	public List<Loja> listarLojas() {
		return manager.createQuery("SELECT l FROM " + Loja.class.getSimpleName() + " l", Loja.class).getResultList();
	}
	
	@Transactional
	public void removerLoja(Loja loja){
		manager.remove(loja);
	}
	
	@Transactional
	public void atualizarLoja(Loja loja) {
		manager.merge(loja);
	}

	@Transactional
	public void adicionarLoja(Loja loja) {
		manager.persist(loja);
	}
}
