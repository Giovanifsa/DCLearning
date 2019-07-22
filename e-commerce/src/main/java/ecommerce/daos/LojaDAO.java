package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ecommerce.models.Loja;

@SuppressWarnings("serial")
public class LojaDAO implements Serializable {
	@Inject
	private EntityManager manager;
	
	public boolean existecpnj(Loja loja) {
		TypedQuery<Loja> query = manager.createQuery("select l from Loja l where l.cnpj = :pCnpj", Loja.class);
		query.setParameter("pCnpj", loja.getCnpj());
		
		try {
			Loja resultado = query.getSingleResult();
			return true;
		}catch(NoResultException ex){
			return false;
		}
	}

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
