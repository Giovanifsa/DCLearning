package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;

@Named
@SessionScoped
public class DadosSessaoBean implements Serializable {
	//Dados do usuário logado
	private Usuario usuarioLogado = null;
	
	@Inject
	TemplateBean templateBean;
	
	//Produtos adicionados ao carrinho
	private ArrayList<ItemCarrinho> produtosCarrinho = new ArrayList<>();

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ArrayList<ItemCarrinho> getProdutosCarrinho() {
		return produtosCarrinho;
	}

	public void setProdutosCarrinho(ArrayList<ItemCarrinho> produtosCarrinho) {
		this.produtosCarrinho = produtosCarrinho;
	}
	
	public void addUmItemAoCarrinho(ItemCarrinho item) {
		// TODO Auto-generated method stub
		this.produtosCarrinho.add(item);
	}
	
	public String removerProduto(Produto produto) {
		
		for (ItemCarrinho item : produtosCarrinho) {
			if(item.getProduto().equals(produto)) {
				produtosCarrinho.remove(item);
				if(produtosCarrinho.isEmpty()) {
					templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto removido!!", true);
					return "carrinhoCompras?faces-redirect=true";
				}
			}
		}
		
		templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto removido!", true);
		return "carrinhoCompras?faces-redirect=true";
	}
	public String removerItemCarrinho(Produto produto, int quantidade) {
		/**
		 * Método para remover determinado produto por quantidade
		 * @param produto
		 * @param quantidade
		 */

		for (ItemCarrinho item : produtosCarrinho) {
			
			if(item.getProduto().equals(produto)) {		// PEGA O PRODUTO
				if(quantidade > item.getQuantidade()) {	//	VERIFICA SE PODE REMOVER
					templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Valor inválido!", true);

				} else {
					item.setQuantidade(item.getQuantidade() - quantidade);
					if(item.getQuantidade() == 0) {	//	SE NÃO TEM MAIS O PRODUTO
						removerProduto(produto);
						return "carrinhoCompras?faces-redirect=true";
					}
				}
			}
		}
		return "carrinhoCompras?faces-redirect=true";
	}
}