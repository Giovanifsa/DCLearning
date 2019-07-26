package ecommerce.beans;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.control.Transactional;
import ecommerce.daos.ProdutoDAO;
import ecommerce.daos.VendaDAO;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Usuario;
import ecommerce.models.Venda;

@Named
@RequestScoped
public class VendasBean {

	@Inject
	DadosSessaoBean dadosSessao;
	@Inject
	LoginBean loginBean;
	@Inject
	VendaDAO vendaDao;
	
	public List<ItemCarrinho> produtosComprados() {
		return dadosSessao.getProdutosCarrinho();
	}
	/**
	 * Adiciona uma compra ao banco de dados
	 * @param itens
	 * @param cliente
	 * @param total
	 */
	@Transactional
	public void adicionarVenda(List<ItemCarrinho> itens, Usuario cliente, BigDecimal total){
		Date aux_data = new Date(System.currentTimeMillis());
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String data = formatador.format(aux_data);
		
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setProdutosComprados(itens);
		venda.setTimestampVenda(data);
		venda.setValor(total);
		vendaDao.adicionarItensCarrinho(itens);
		vendaDao.adicionarVenda(venda);
	}
	@Transactional
	public Venda gerarPedido(int id) {
		System.out.println(id);
		return vendaDao.getVenda(id);
	}

}
