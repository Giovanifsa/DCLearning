package ecommerce.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@RequestScoped
public class Lojabean{

	@Inject
	ProdutoDAO produtoDao;
	
	@Inject
	LoginBean loginBean;
	
	@Inject
	TemplateBean templateBean;
	
	public List<Produto> listarProdutos() {
	
		return produtoDao.listarProdutos();
	}

	public String detalheSobreProduto(Produto produto) {
		return "produto.xhtml?faces-redirect=true&produtoId=" + produto.getId();
	}

	public String cadastreSuaLoja() {
		if (!loginBean.usuarioEstaLogado()) {
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Inicie uma sessão para cadastrar sua loja.",
					true);
			return "login?faces-redirect=true&redirecionamento=novaLoja";
		} else {
			return "novaLoja?faces-redirect=true";
		}
	}
	
	public String cadastreSeusProdutos() {
		if(!loginBean.usuarioEstaLogado()) {
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Inicie uma sessão para cadastrar seus produtos.", true);
			return "login?faces-redirect=true&redirecionamento=novoProduto";
		} else {
			return "novoProduto?faces-redirect=true";
		}
	}
	
}
