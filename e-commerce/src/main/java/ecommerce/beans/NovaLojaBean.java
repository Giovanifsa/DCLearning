package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
	//Página novaLoja.xhtml
	private UIComponent campoNomeFantasia;
	private UIComponent campoCNPJ;
	private UIComponent campoDespesasTotais;
	
	private int editandoId = -1;
	private String cadastroNomeFantasia;
	private String cadastroCNPJ;
	private String cadastroDespesasTotais;
	private int editandoQuantiaProdutos;

	@Inject
	private LojaDAO dao;
	
	@Inject
	private LoginBean loginBean;

	@Inject
	private TemplateBean pagTemplate;
	
	public void validarNomeFantasia(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty() || value.toString().isBlank()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "O nome não deve estar vazio!", null));
		}
	}
	
	public void validarCNPJ(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		//Pattern pattern = Pattern.compile("^(\\d\\d).(\\d\\d\\d).(\\d\\d\\d)\\/(\\d\\d\\d\\d)-(\\d\\d)$");
		Pattern pattern = Pattern.compile("^\\d\\d.\\d\\d\\d.\\d\\d\\d\\/\\d\\d\\d\\d-\\d\\d$");
		Matcher matcher = pattern.matcher(value.toString());
		
		//String[] blocos = new String[5];
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "CNPJ inválido! Deve ser: (XX.XXX.XXX/YYYY-ZZ)", null));
		}
	}
	
	public void validarDespesasTotais(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		try {
			new BigDecimal(value.toString());
		} catch (NumberFormatException ex) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Despesas totais deve ser um valor de ponto flutuante maior que zero!", null));
		}
	}

	@Transactional
	public String salvarLoja() {
		Loja l = new Loja(loginBean.getUsuarioLogado(), cadastroNomeFantasia, cadastroCNPJ, new BigDecimal(cadastroDespesasTotais));
		
		try {
			if (editandoId != -1) {
				l.setQuantiaProdutos(editandoQuantiaProdutos);
				l.setId(editandoId);
				
				dao.atualizarLoja(l);
				pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Loja " + l.getNomeFantasia() + " editada com sucesso!", true);
				
				return "novaLoja?faces-redirect=true";
			}
			
			else {
				dao.adicionarLoja(l);
				pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Loja " + l.getNomeFantasia() + " cadastrada com sucesso!", true);
				
				return "novaLoja?faces-redirect=true";
			}
		} catch (PersistenceException ex) {
			if (ex.getCause() instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(campoCNPJ.getClientId(), 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este CNPJ já está cadastrado!", null));
			}
		}
		
		return null;
	}
	
	public void editar(Loja loja) {
		editandoId = loja.getId();
		
		cadastroNomeFantasia = loja.getNomeFantasia();
		cadastroCNPJ = loja.getCnpj();
		cadastroDespesasTotais = loja.getDespesasTotais().toString();
		editandoQuantiaProdutos = loja.getQuantiaProdutos();
	}
	
	public void cancelarEdicao() {
		editandoId = -1;
		
		cadastroNomeFantasia = null;
		cadastroCNPJ = null;
		cadastroDespesasTotais = null;
		editandoQuantiaProdutos = 0;
	}
	
	public boolean estaEditando() {
		return editandoId != -1;
	}

	/*
	@Transactional
	public String remover(Loja loja) {
		dao.removerLoja(loja);
		
		if (editandoId == loja.getId()) {
			editandoId = -1;
		}
		
		return "novaLoja?faces-redirect=true";
	}*/

	public List<Loja> listarLojas() {
		return dao.listarLojasUsuario(loginBean.getUsuarioLogado());
	}

	public UIComponent getCampoNomeFantasia() {
		return campoNomeFantasia;
	}

	public void setCampoNomeFantasia(UIComponent campoNomeFantasia) {
		this.campoNomeFantasia = campoNomeFantasia;
	}

	public UIComponent getCampoCNPJ() {
		return campoCNPJ;
	}

	public void setCampoCNPJ(UIComponent campoCNPJ) {
		this.campoCNPJ = campoCNPJ;
	}

	public UIComponent getCampoDespesasTotais() {
		return campoDespesasTotais;
	}

	public void setCampoDespesasTotais(UIComponent campoDespesasTotais) {
		this.campoDespesasTotais = campoDespesasTotais;
	}

	public String getCadastroNomeFantasia() {
		return cadastroNomeFantasia;
	}

	public void setCadastroNomeFantasia(String cadastroNomeFantasia) {
		this.cadastroNomeFantasia = cadastroNomeFantasia;
	}

	public String getCadastroCNPJ() {
		return cadastroCNPJ;
	}

	public void setCadastroCNPJ(String cadastroCNPJ) {
		this.cadastroCNPJ = cadastroCNPJ;
	}

	public String getCadastroDespesasTotais() {
		return cadastroDespesasTotais;
	}

	public void setCadastroDespesasTotais(String cadastroDespesasTotais) {
		this.cadastroDespesasTotais = cadastroDespesasTotais;
	}
}
