
package ecommerce.control;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import ecommerce.beans.LoginBean;

public class PageRestrictor implements PhaseListener {
	@Inject
	private LoginBean loginBean;
	
	@Override
	public void afterPhase(PhaseEvent pEvent) {
		FacesContext context = pEvent.getFacesContext();
		
		/*
		 * Restricted pages should be:
		 * finalizecart.xhtml
		 * */
		
		/*
		if(!"/login.xhtml".equals(context.getViewRoot().getViewId()) && !loginBean.isUserLogged()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/login?faces-redirect=true");
			context.renderResponse();
		}*/
	} 

	@Override
	public void beforePhase(PhaseEvent pEvent) {}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
