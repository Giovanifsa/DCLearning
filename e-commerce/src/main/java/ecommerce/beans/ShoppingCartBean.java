package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ecommerce.models.CartProduct;
import ecommerce.models.Product;

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {
	private ArrayList<CartProduct> selectedProducts = new ArrayList<>();
	
	public double calcFinalPrice() {
		double price = 0.0d;
		
//		for (CartProduct e : selectedProducts) {
//			price += (e.getProduct().getPrice() * e.getQuantity());
//		}
//		
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
