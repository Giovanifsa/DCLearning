package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.FileDao;
import ecommerce.daos.ProductDao;
import ecommerce.models.CartProduct;
import ecommerce.models.Product;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {
	@Inject
	private ProductDao productDao;
	
	@Inject
	private FileDao fileDao;
	
	@Inject
	private HashMechanism hashing;
	
	private ArrayList<CartProduct> selectedProducts = new ArrayList<>();
	
	@PostConstruct
	public void postContruct() {
		Product p = productDao.getProduct(1);
		CartProduct cartProd = new CartProduct(p, 27);
		
		selectedProducts.add(cartProd);
	}
	
	public String removeCartProduct(CartProduct cartProduct) {
		for (CartProduct e : selectedProducts) {
			if (e.equals(cartProduct)) {
				selectedProducts.remove(e);
				
				break;
			}
		}
		
		return "cart";
	}
	
	public double calcFinalPrice() {
		double price = 0.0d;
		
		for (CartProduct e : selectedProducts) {
			price += (e.getProduct().getPrice() * e.getQuantity());
		}
		
		return price;
	}
	
	public void setQuantity(Product p, int quantity) {
		for (CartProduct e : selectedProducts) {
			if (e.getProduct().equals(p)) {
				if (quantity == 0) {
					selectedProducts.remove(e);
				}
				
				else if (quantity < 0) {
					FacesContext.getCurrentInstance().addMessage("shoppingCartMessage", new FacesMessage("É necessário um valor acima ou igual a zero."));
				}
				
				else {
					e.setQuantity(quantity);
				}
				
				break;
			}
		}
	}
	
	public int getCartQuantity() {
		int quantity = 0;
		
		for (CartProduct e : selectedProducts) {
			quantity += e.getQuantity();
		}
		
		return quantity;
	}
	
	public List<CartProduct> getSelectedProducts() {
		return selectedProducts;
	}
}
