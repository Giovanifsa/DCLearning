package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ecommerce.beans.TemplateBean;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;
import ecommerce.models.Venda;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class VendaDAO implements Serializable {
	@Inject
	private EntityManager em;
	@Inject 
	TemplateBean t;
	
	public void adicionarVenda(Venda v) {
		em.persist(v);
	}
	public void adicionarItensCarrinho(List<ItemCarrinho> itens) {
		for (ItemCarrinho item : itens) {
			em.persist(item);
		}
	}
	
	public Venda getVenda(int cliente_id) {
		return em.find(Venda.class, cliente_id);						
	}
}