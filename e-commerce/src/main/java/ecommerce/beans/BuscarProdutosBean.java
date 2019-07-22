package ecommerce.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class BuscarProdutosBean implements Serializable {
	//ViewParam
	private String buscando;
	
	@Inject
	private TemplateBean pagTemplate;

	public String getBuscando() {
		return buscando;
	}

	public void setBuscando(String buscando) {
		this.buscando = buscando;
		pagTemplate.setNavbarTextoPesquisa(buscando);
	}
}
