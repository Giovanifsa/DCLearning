package ecommerce.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.codec.binary.Base32;

import ecommerce.models.LocalGrowl;

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
	
	private UIForm formGrowlSuperiorEsquerdo;
	private UIForm formGrowlSuperiorDireito;
	private UIForm formGrowlSuperiorCentro;
	
	private UIForm formGrowlInferiorEsquerdo;
	private UIForm formGrowlInferiorDireito;
	private UIForm formGrowlInferiorCentro;
	
	public UIComponent getTemplateNavbar() {
		return templateNavbar;
	}

	public void setTemplateNavbar(UIComponent templateNavbar) {
		this.templateNavbar = templateNavbar;
	}

	public UIForm getFormGrowlSuperiorEsquerdo() {
		return formGrowlSuperiorEsquerdo;
	}

	public void setFormGrowlSuperiorEsquerdo(UIForm formGrowlSuperiorEsquerdo) {
		this.formGrowlSuperiorEsquerdo = formGrowlSuperiorEsquerdo;
	}

	public UIForm getFormGrowlSuperiorDireito() {
		return formGrowlSuperiorDireito;
	}

	public void setFormGrowlSuperiorDireito(UIForm formGrowlSuperiorDireito) {
		this.formGrowlSuperiorDireito = formGrowlSuperiorDireito;
	}

	public UIForm getFormGrowlInferiorEsquerdo() {
		return formGrowlInferiorEsquerdo;
	}

	public void setFormGrowlInferiorEsquerdo(UIForm formGrowlInferiorEsquerdo) {
		this.formGrowlInferiorEsquerdo = formGrowlInferiorEsquerdo;
	}

	public UIForm getFormGrowlInferiorDireito() {
		return formGrowlInferiorDireito;
	}

	public void setFormGrowlInferiorDireito(UIForm formGrowlInferiorDireito) {
		this.formGrowlInferiorDireito = formGrowlInferiorDireito;
	}

	public UIForm getFormGrowlSuperiorCentro() {
		return formGrowlSuperiorCentro;
	}

	public void setFormGrowlSuperiorCentro(UIForm formGrowlSuperiorCentro) {
		this.formGrowlSuperiorCentro = formGrowlSuperiorCentro;
	}

	public UIForm getFormGrowlInferiorCentro() {
		return formGrowlInferiorCentro;
	}

	public void setFormGrowlInferiorCentro(UIForm formGrowlInferiorCentro) {
		this.formGrowlInferiorCentro = formGrowlInferiorCentro;
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
	
	/**
	 * Mostra uma mensagem growl no topo da tela, disponível para todas as páginas.
	 * @param localGrowl Local do growl na tela.
	 * @param severidade Severidade da mensagem.
	 * @param mensagem String contendo o texto da mensagem.
	 * @param usarEscopoFlash Utilizar ou não o escopo flash para a
	 *  mensagem (será mostrada no próximo request).
	 */
	@SuppressWarnings("unchecked")
	public void adicionarMensagemGrowl(LocalGrowl localGrowl, Severity severidade, String mensagem, boolean usarEscopoFlash) {
		UIForm formGrowl;
		
		switch (localGrowl) {
			case SUPERIOR_ESQUERDO : {
				formGrowl = formGrowlSuperiorEsquerdo;
				break;
			}
			
			case SUPERIOR_DIREITO : {
				formGrowl = formGrowlSuperiorDireito;
				break;
			}
			
			case SUPERIOR_CENTRO : {
				formGrowl = formGrowlSuperiorCentro;
				break;
			}
			
			case INFERIOR_ESQUERDO : {
				formGrowl = formGrowlInferiorEsquerdo;
				break;
			}
			
			case INFERIOR_DIREITO : {
				formGrowl = formGrowlInferiorDireito;
				break;
			}
			
			case INFERIOR_CENTRO : {
				formGrowl = formGrowlInferiorCentro;
				break;
			}
			
			default: {
				formGrowl = formGrowlSuperiorEsquerdo;
				break;
			}
		}
		
		FacesContext.getCurrentInstance().addMessage(formGrowl.getClientId(), new FacesMessage(severidade, mensagem, null));
		
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
			Base32 base32 = new Base32();
			
			//Encode em base32 necessário, pois o usuário pode procurar com caracteres especiais na pesquisa, e isso quebraria o site.
			return "buscarProdutos?faces-redirect=true&b32Buscando=" + base32.encodeAsString(navbarTextoPesquisa.getBytes());
		}

		return null;
	}
}
