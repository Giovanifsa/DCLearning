package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.ProductDao;
import ecommerce.models.CartProduct;
import ecommerce.models.Product;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

@Named
@SessionScoped
public class CartBean implements Serializable {
	@Inject
	private ProductDao productDao;
	
	@Inject
	private HashMechanism hashing;
	
	private ArrayList<CartProduct> selectedProducts = new ArrayList<>();
	
	public String removeCartProduct(CartProduct cartProduct) {
		for (CartProduct e : selectedProducts) {
			if (e.equals(cartProduct)) {
				selectedProducts.remove(e);
				
				break;
			}
		}
		
		return "cart";
	}
	
	public BigDecimal calcFinalPrice() {
		BigDecimal price = new BigDecimal(0);
		
		for (CartProduct e : selectedProducts) {
			price = price.add(e.getProduct().getPrice().multiply(new BigDecimal(e.getQuantity())));
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
