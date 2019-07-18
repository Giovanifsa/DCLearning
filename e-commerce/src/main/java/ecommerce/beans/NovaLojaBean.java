package ecommerce.beans;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import ecommerce.daos.LojaDAO;
import ecommerce.models.Loja;

@SuppressWarnings("serial")
public class NovaLojaBean implements Serializable {
	private Loja loja = new Loja();
	
	@Inject
	private LojaDAO dao;

	public String salvarLoja() {
		System.out.println("Gravando loja" + loja.getNomeFantasia());
		dao.adicionarLoja(loja);
		
		return "/newproduct?faces-redirect=true";
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public List<Loja> listarTodasLojas() {
		return dao.listarLojas();
	}

}
