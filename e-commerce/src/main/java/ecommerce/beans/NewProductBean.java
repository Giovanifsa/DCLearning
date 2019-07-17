package ecommerce.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import ecommerce.daos.ProductDao;
import ecommerce.models.Product;

@Named
@ViewScoped
public class NewProductBean implements Serializable {
	private Product tempProduct = new Product();
	private UploadedFile tempProductFile;
	
	@Inject
	private ProductDao productDao;
	
	public void saveProduct() {
		
	}
}
