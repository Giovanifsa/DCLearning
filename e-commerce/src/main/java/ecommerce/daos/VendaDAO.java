package ecommerce.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ecommerce.beans.TemplateBean;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.ItemVendido;
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
	private ProdutoDAO produtoDao;

	public List<Venda> listarComprasUsuario(Usuario usuario) {
		return em.createQuery("SELECT v FROM " + Venda.class.getSimpleName() + " v WHERE v.cliente.id = :idUsuario ORDER BY v.id DESC", Venda.class)
				.setParameter("idUsuario", usuario.getId())
				.getResultList();
	}
	
	public void adicionarVenda(Venda v) {
		//Atualiza a quantia de itens vendidos
		for (ItemCarrinho item : v.getProdutosComprados()) {
			produtoDao.produtoVendido(item.getProduto(), item.getQuantidade());
		}
		
		em.persist(v);
	}
	
	public void adicionarItensCarrinho(List<ItemCarrinho> itens) {
		for (ItemCarrinho item : itens) {
			em.persist(item);
		}
	}
	
	public Venda getVenda(int id) {
		return em.find(Venda.class, id);						
	}
}