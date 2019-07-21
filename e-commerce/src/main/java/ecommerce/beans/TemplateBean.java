package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
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
	private UIForm formNavBar;
	
	public UIForm getFormNavBar() {
		return formNavBar;
	}

	public void setFormNavBar(UIForm formNavBar) {
		this.formNavBar = formNavBar;
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
		FacesContext.getCurrentInstance().addMessage(formNavBar.getClientId(), new FacesMessage(severidade, mensagem, null));
		
		if (usarEscopoFlash) {			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
	}
}
