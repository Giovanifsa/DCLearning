package ecommerce.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.control.Transactional;
import ecommerce.daos.LojaDAO;
import ecommerce.models.Loja;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class NovaLojaBean implements Serializable {
	private Loja loja = new Loja();

	@Inject
	LojaDAO dao;
	
	@Inject
	LoginBean loginBean;
	
	@Inject
	TemplateBean templateBean;

	@Transactional
	public String salvarLoja() {
		System.out.println("Gravando loja" + loja.getNomeFantasia());

		
		boolean existecnpj = dao.existecpnj(this.loja);
		if (existecnpj) {
		
			pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "CNPJ já cadastrado",true);
		} else {
			this.dao.adicionarLoja(this.loja);
			this.loja = new Loja();
		}


		return "novaLoja?faces-redirect=true";
	}

	@Transactional
	public String remover(Loja loja) {
		System.out.println("Removendo loja" + loja.getNomeFantasia());
		this.dao.removerLoja(loja);

		return "novaLoja?faces-redirect=true";
	}

	public void carregaloja(Loja loja) {
		System.out.println("Carregando loja...");
		this.loja = loja;
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
	
	public String cadastrarLoja() {
		if(!loginBean.usuarioEstaLogado()) {
			templateBean.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Inicie uma sessão para cadastrar sua loja.", true);
			return "login?faces-redirect=true";
		} else {
			return "carrinhoCompras?faces-redirect=true";
		}
	}

}
