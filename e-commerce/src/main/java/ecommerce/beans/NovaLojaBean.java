package ecommerce.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import ecommerce.control.Transactional;
import ecommerce.daos.LojaDAO;
import ecommerce.models.Loja;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class NovaLojaBean implements Serializable {
	private Loja loja = new Loja();

	@Inject
	private LojaDAO dao;
	
	@Inject
	private LoginBean loginBean;

	@Inject
	private TemplateBean pagTemplate;
	
	private boolean editando = false;

	@Transactional
	public String salvarLoja() {
		if (!editando) {
			try {
				dao.adicionarLoja(loja);
				this.loja = new Loja();
			} catch (PersistenceException ex) {
				//Implementação do hibernate para violação de unique
				if (ex.getCause() instanceof ConstraintViolationException) {
					pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Uma loja com este CNPJ já foi cadastrada.", true);
				}
			}
		}
		
		else {
			dao.atualizarLoja(loja);
			pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Loja " + loja.getNomeFantasia() + " atualizada com sucesso!", true);
			
			this.loja = new Loja();
			editando = false;
		}

		return "novaLoja?faces-redirect=true";
	}
	
	public void editar(Loja loja) {
		this.loja = loja;
		editando = true;
	}

	@Transactional
	public String remover(Loja loja) {
		dao.removerLoja(loja);
		return "novaLoja?faces-redirect=true";
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public List<Loja> listarLojas() {
		return dao.listarLojasUsuario(loginBean.getUsuarioLogado());
	}

}
