package ecommerce.beans;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;

@Model
@ManagedBean
@ViewScoped
public class SpinnerBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
