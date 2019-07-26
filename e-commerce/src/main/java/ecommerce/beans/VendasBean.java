package ecommerce.beans;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.control.Transactional;
import ecommerce.daos.VendaDAO;
import ecommerce.models.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	//Página dadosPedido
	private int pedidoId = -1;
	private Venda pedido;
	
	@Inject
	private CarrinhoBean carrinhoBean;
	@Inject
	private LoginBean loginBean;
	@Inject
	private VendaDAO vendaDao;
	
	public List<Venda> listarComprasUsuario() {
		return vendaDao.listarComprasUsuario(loginBean.getUsuarioLogado());
	}

	@Transactional
	public void salvarVenda(){
		Venda venda = new Venda();
		venda.setCliente(loginBean.getUsuarioLogado());
		venda.setProdutosComprados(carrinhoBean.produtosCarrinho());
		venda.setDataVenda(Date.from(Instant.now()));
		venda.setValor(carrinhoBean.calcularPrecoFinal());
		
		vendaDao.adicionarItensCarrinho(carrinhoBean.produtosCarrinho());
		vendaDao.adicionarVenda(venda);
	}
	
	public void carregarPedido() {
		if (pedidoId != -1) {
			pedido = vendaDao.getVenda(pedidoId);
		}
	}
	
	public boolean pedidoFoiCarregado() {
		return pedido != null;
	}

	public Venda getPedido() {
		return pedido;
	}

	public void setPedido(Venda pedido) {
		this.pedido = pedido;
	}
	
	public void pedidoSelecionado(Venda pedido) {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		
		handler.handleNavigation(context, null, "/dadosPedido?faces-redirect=true&pedidoId=" + pedido.getId());
		context.renderResponse();
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}
}
