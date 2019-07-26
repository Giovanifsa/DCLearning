package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.UsuarioDAO;
import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CarrinhoBean implements Serializable {

	@Inject
	private VendasBean vendasBean;

	@Inject
	private DadosSessaoBean dadosSessao;

	@Inject
	private TemplateBean templateBean;

	@Inject
	private LoginBean loginBean;

	private int produtoAtualizado;

	/**
	 * Calcula o preço final (total) da soma do preço de todos os itens e suas
	 * quantidades.
	 * 
	 * @return Valor total de compra.
	 */

	public int getProdutoAtualizado() {
		return produtoAtualizado;
	}

	public void setMax(int produtoAtualizado) {
		this.produtoAtualizado = produtoAtualizado;
	}

	public BigDecimal calcularPrecoFinal() {
		BigDecimal price = new BigDecimal(0);

		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			price = price.add(e.getProduto().calcularPrecoPelaQuantidade((e.getQuantidade())));
		}

		return price;
	}

	public void adicionarAoCarrinho(Produto p, int quantidade) {
		// Encontra o mesmo produto no carrinho e soma o total com ele
		for (ItemCarrinho item : dadosSessao.getProdutosCarrinho()) {
			if (item.getProduto().equals(p)) {
				item.setQuantidade(item.getQuantidade() + quantidade);

				return;
			}
		}
		dadosSessao.getProdutosCarrinho().add(new ItemCarrinho(p, quantidade));
	}

	/**
	 * Altera a quantidade de um item no carrinho
	 * 
	 * @param p          Produto cuja quantidade será alterada.
	 * @param quantidade Nova quantidade de itens.
	 */
	@Deprecated
	public void setQuantidadeItem(Produto p, int quantidade) {
		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			if (e.getProduto().equals(p)) {
				if (quantidade == 0) {
					dadosSessao.getProdutosCarrinho().remove(e);
				}

				else if (quantidade < 0) {
					FacesContext.getCurrentInstance().addMessage("shoppingCartMessage",
							new FacesMessage("É necessário um valor acima ou igual a zero."));
				}

				else {
					e.setQuantidade(quantidade);
				}

				break;
			}
		}
	}

	/**
	 * Calcula a quantidade total de itens no carrinho, isto é, soma total da
	 * quantidade de todos os produtos do carrinho.
	 * 
	 * @return Quantidade total dos produtos do carrinho.
	 */
	public int getQuantidadeItens() {
		int quantidade = 0;

		for (ItemCarrinho e : dadosSessao.getProdutosCarrinho()) {
			quantidade += e.getQuantidade();
		}

		return quantidade;
	}

	public List<ItemCarrinho> produtosCarrinho() {
		return dadosSessao.getProdutosCarrinho();
	}

	public int selecionarQuantidade() {

		int quantidade = 0;
		if (quantidade == 1) {
			return quantidade;
		}
		return 0;
	}

	/**
	 * Esse método retorna o preço total de um produto no carrinho
	 * 
	 * @param produto
	 * @return Double
	 */
	public BigDecimal precoPorQuantidade(Produto produto) {
		List<ItemCarrinho> itens = dadosSessao.getProdutosCarrinho();
		BigDecimal valor = new BigDecimal(0);
		int quantidade = 0;
		for (ItemCarrinho item : itens) {
			if (item.getProduto().equals(produto)) {
				valor = produto.calcularPreco();
				quantidade = item.getQuantidade();
			}
		}
		return valor.multiply(new BigDecimal(quantidade));
	}

	public String removerProduto(Produto produto) {
		ArrayList<ItemCarrinho> produtos = dadosSessao.getProdutosCarrinho();

		for (int i = 0; i < produtos.size(); i++) {
			if (produtos.get(i).getProduto().equals(produto)) {
				produtos.remove(i);
			}
		}

		templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto removido!", true);
		return "carrinhoCompras?faces-redirect=true";
	}

	public String voltarParaLoja() {
		return "loja?faces-redirect=true";
	}

	public String finalizarCompra(int id) {

		if (!loginBean.usuarioEstaLogado()) {
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO,
					"Para finalizar a sua compra, é necessário fazer o Login!", true);
			return "login?faces-redirect=true&redirecionamento=carinhoCompras.xhtml";
		} else {
			List<ItemCarrinho> itens = dadosSessao.getProdutosCarrinho();
			Usuario cliente = loginBean.getUsuarioLogado();
			BigDecimal total = calcularPrecoFinal();

			vendasBean.adicionarVenda(itens, cliente, total);
			dadosSessao.setProdutosCarrinho(new ArrayList<ItemCarrinho>());
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Compra realizado com sucesso!", true);
			return "pedido?faces-redirect=true";
		}
	}
}