package ecommerce.models;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;

@Model
@ViewScoped
public class Spinner implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int valor;

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
