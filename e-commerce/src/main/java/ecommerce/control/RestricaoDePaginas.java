package ecommerce.control;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import ecommerce.beans.CarrinhoBean;
import ecommerce.beans.LoginBean;
import ecommerce.beans.TemplateBean;

public class RestricaoDePaginas implements PhaseListener {
	@Inject
	private LoginBean loginBean;
	@Inject
	private TemplateBean pagTemplate;
	
	@Override
	public void afterPhase(PhaseEvent pEvent) {
		FacesContext context = pEvent.getFacesContext();
		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (!loginBean.usuarioEstaLogado()) {
			if (context.getViewRoot().getViewId().startsWith("/novaLoja.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=novaLoja");
				context.renderResponse();
			}
			
			else if (context.getViewRoot().getViewId().startsWith("/novoProduto.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=novoProduto");
				context.renderResponse();
			}
			
			else if (context.getViewRoot().getViewId().startsWith("/pedidos.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=pedidos");
				context.renderResponse();
			}
			
			else if (context.getViewRoot().getViewId().startsWith("/dadosPedido.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=pedidos");
				context.renderResponse();
			}
		}
		
		else {
			if (context.getViewRoot().getViewId().startsWith("/login.xhtml")) {
				handler.handleNavigation(context, null, "/loja?faces-redirect=true");
				context.renderResponse();
			}
		}
	} 

	@Override
	public void beforePhase(PhaseEvent pEvent) {}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
