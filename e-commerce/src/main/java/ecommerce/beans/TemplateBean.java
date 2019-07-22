package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Bean com funcionalidades gerais para todas as páginas.
 * Atualmente utilizado para fazer binding com componentes do template, como
 * navbar, growl, etc.
 * 
 * @author Giovani
 *
 */
@Named
@ViewScoped
public class TemplateBean implements Serializable {
	private String navbarTextoPesquisa;
	private UIComponent templateNavbar;
	
	public UIComponent getTemplateNavbar() {
		return templateNavbar;
	}

	public void setTemplateNavbar(UIComponent templateNavbar) {
		this.templateNavbar = templateNavbar;
	}

	/**
	 * Mostra uma mensagem no topo da tela, disponível para todas as páginas.
	 * @param severidade Severidade da mensagem.
	 * @param mensagem String contendo o texto da mensagem.
	 * @param usarEscopoFlash Utilizar ou não o escopo flash para a
	 *  mensagem (será mostrada no próximo request).
	 */
	@SuppressWarnings("unchecked")
	public void adicionarMensagem(Severity severidade, String mensagem, boolean usarEscopoFlash) {
		FacesContext.getCurrentInstance().addMessage(templateNavbar.getClientId(), new FacesMessage(severidade, mensagem, null));
		
		if (usarEscopoFlash) {			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	public String getNavbarTextoPesquisa() {
		return navbarTextoPesquisa;
	}

	public void setNavbarTextoPesquisa(String navbarTextoPesquisa) {
		this.navbarTextoPesquisa = navbarTextoPesquisa;
	}
	
	public String pesquisarProduto() {
		if (navbarTextoPesquisa != null && !navbarTextoPesquisa.isEmpty() && !navbarTextoPesquisa.isBlank()) {
			return "buscarProdutos?faces-redirect=true&buscando=";
		}

		return null;
	}
}
