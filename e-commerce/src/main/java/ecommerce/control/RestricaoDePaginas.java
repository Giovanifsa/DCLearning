
package ecommerce.control;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import ecommerce.beans.CartBean;
import ecommerce.beans.LoginBean;

public class RestricaoDePaginas implements PhaseListener {
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CartBean cartBean;
	
	@Override
	public void afterPhase(PhaseEvent pEvent) {
		FacesContext context = pEvent.getFacesContext();
		/* 
		 * As páginas restritas são:
		 * finalizarCompra.xhtml -> Caso o usuário não esteja logado ou esteja com o carrinho vazio
		 * 
		 */
		
		if (context.getViewRoot().getViewId().equals("/finalizarCompra.xhtml")) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			
			if (!loginBean.isUserLogged()) {
				
				handler.handleNavigation(context, null, "/login?faces-redirect=true&redirecionamento=finalizarCompra");
				context.renderResponse();
			}
			
			else if (cartBean.getCartQuantity() <= 0) {
				handler.handleNavigation(context, null, "/listarProdutos?faces-redirect=true");
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
