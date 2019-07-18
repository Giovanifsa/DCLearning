package ecommerce.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import ecommerce.control.Transactional;
import ecommerce.daos.LojaDAO;
import ecommerce.models.Loja;

@SuppressWarnings("serial")
@Model
public class NovaLojaBean implements Serializable {
	private Loja loja = new Loja();
	
	@Inject
	private LojaDAO dao;

	@Transactional
	public String salvarLoja() {
		System.out.println("Gravando loja" + loja.getNomeFantasia());
		
		if(this.loja.getCnpj() ==  null) {
			this.dao.adicionarLoja(this.loja);
		}else {
			this.dao.atualizarLoja(this.loja);
		}
		this.loja = new Loja();
		
		return "/novoProduto?faces-redirect=true";
	}

	@Transactional
	public void remover(Loja loja) {
		System.out.println("Removendo loja" + loja.getNomeFantasia());
		this.dao.removerLoja(loja);
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
