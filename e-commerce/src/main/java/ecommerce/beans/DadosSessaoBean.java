package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ecommerce.models.ItemCarrinho;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;

@Named
@SessionScoped
public class DadosSessaoBean implements Serializable {
	//Dados do usuário logado
	private Usuario usuarioLogado = null;
	
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
	
	
	public String removerProduto(Produto produto, int quantidade) {
		/**
		 * Método para remover determinado produto por quantidade
		 * @param produto
		 * @param quantidade
		 */

		FacesContext facesContext = FacesContext.getCurrentInstance();
		for (ItemCarrinho item : produtosCarrinho) {
			
			if(item.getProduto().getId() == produto.getId()) {// JA TEM NO CARRINHO
				if(quantidade > item.getQuantidade()) {	//	VERIFICA SE PODE REMOVER
					facesContext.getExternalContext().getFlash().setKeepMessages(true);
					facesContext.addMessage(null, new FacesMessage("Valor inválido!"));
					return "carrinhoCompras?faces-redirect=true";
				} else {
					int novaQuantidade = item.getQuantidade() - quantidade;
					item.setQuantidade(novaQuantidade);
				}
			}
		}
		return "carrinhoCompras?faces-redirect=true";
	}

	
	
	public int qtdeDeUmItemNoCarrinho(Produto produto) {
		/**
		 * Retorna a quantidade de determinado item no carrinho
		 */

		int valor = 0;
		
		for (ItemCarrinho item : produtosCarrinho) {
			if(item.getProduto().getId() == produto.getId()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getFlash().setKeepMessages(true);
				facesContext.addMessage("t", new FacesMessage("Não funcionando: " +
						item.getQuantidade() ));
				
			}
		}
		return valor;
	}

}
