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
	TemplateBean templateBean;
	@Inject
	ProdutoDAO produtoDao;
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
	 * @param valor
	 * @param cliente
	 */
	@Transactional
	public void adicionarVenda(List<ItemCarrinho> itens, Usuario cliente, BigDecimal total){
		Date aux_data = new Date(System.currentTimeMillis());
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String data = formatador.format(aux_data);
		
		Venda venda = new Venda();
		venda.setId(cliente.getId());
		venda.setCliente(cliente);
		venda.setProdutosComprados(itens);
		venda.setTimestampVenda(data);
		venda.setValor(total);
		templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "|Cliente: " + venda.getCliente().getNome() 
				+ " | " + venda.getCliente().getId() + "\n|Data: " +venda.getTimestampVenda() + "\n|Valor:" + venda.getValor()
				+ " |Produto: " + venda.getProdutosComprados().get(0) +"\n\n[ " + itens + " | " + cliente + " | " + total +" " + " ]", true);
		try {
			
			vendaDao.adicionarVenda(venda);
		} catch(Exception e) {
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "" + e, true);
		}
		
		
		
	}
	
//	public Venda gerarPedido() {
//		int id = loginBean.getUsuarioLogado().getId();
//		Venda venda = vendaDao.gerarPedido(id);
//
//		return venda;
//	}

}
