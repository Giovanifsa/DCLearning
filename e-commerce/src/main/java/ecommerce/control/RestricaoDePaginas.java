package ecommerce.control;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import ecommerce.beans.CarrinhoBean;
import ecommerce.beans.LoginBean;

public class RestricaoDePaginas implements PhaseListener {
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CarrinhoBean carrinhoBean;
	
	@Override
	public void afterPhase(PhaseEvent pEvent) {
		FacesContext context = pEvent.getFacesContext();
		NavigationHandler handler = context.getApplication().getNavigationHandler();

		if (!loginBean.usuarioEstaLogado()) {
			if (context.getViewRoot().getViewId().equals("/novaLoja.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=novaLoja");
				context.renderResponse();
			}
			
			else if (context.getViewRoot().getViewId().equals("/novoProduto.xhtml")) {
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=novoProduto");
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
