package ecommerce.daos;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.models.ItemCarrinho;
import ecommerce.models.Venda;

@Named
@RequestScoped
public class VendaDAO implements Serializable {
	@Inject
	private EntityManager em;
	
	public void adicionarItensCarrinho(ItemCarrinho... itemCarrinho) {
		for (ItemCarrinho i : itemCarrinho) {
			em.persist(i);
		}
	}
	
	public void adicionarVenda(Venda v) {
		em.persist(v);
	}
	
	public Venda getVenda(int id) {
		return em.find(Venda.class, id);
	}
}
