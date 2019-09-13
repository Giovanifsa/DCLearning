package ecommerce.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@RequestScoped
public class LojaBean {

	@Inject
	private ProdutoDAO produtoDao;
	@Inject
	private LoginBean loginBean;
	@Inject
	private TemplateBean templateBean;
	
	public List<Produto> listarProdutos() {
		return produtoDao.buscarProdutosRecentes(30);
	}
}
