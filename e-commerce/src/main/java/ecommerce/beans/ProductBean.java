package ecommerce.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ecommerce.daos.ProductDao;
import ecommerce.models.Product;

@Named
@ViewScoped
public class ProductBean implements Serializable {
	@Inject
	private ProductDao productDao;
	
	public StreamedContent getProductImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        
        else {
            Product prod = productDao.getProduct(Integer.valueOf(context.getExternalContext().getRequestParameterMap().get("product_id")));
            return new DefaultStreamedContent(new ByteArrayInputStream(prod.getProdImage().getFileData()));
        }
    }
}
