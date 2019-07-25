package ecommerce.daos;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.beans.TemplateBean;
import ecommerce.models.ItemCarrinho;
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
	
//	public Venda gerarPedido(int id) {
//		Venda vendas = new Venda();
//		try {
//			em.getTransaction().begin();
//			
//			CriteriaBuilder criteria = em.getCriteriaBuilder();
//			CriteriaQuery<Venda> query = criteria.createQuery(Venda.class);
//			Root<Venda> rota = query.from(Venda.class);
//			query.orderBy(criteria.desc(rota.get(vendas.getTimestampVenda())));
//			em.getTransaction().commit();
//						
//		} catch (Exception e) {
//			t.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "" + e, true);
//		}
//		return vendas;
//	}
	
}