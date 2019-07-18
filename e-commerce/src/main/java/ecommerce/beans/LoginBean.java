package ecommerce.beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.daos.UsuarioDAO;
import ecommerce.models.Usuario;
import ecommerce.tools.MecanismoDeHash;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	public static final int PASSWORD_MIN_SIZE = 8;
	public static final String EMAIL_REGEX = 
					"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" + 
					"*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7" + 
					"f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a" + 
					"-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25" + 
					"[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[" + 
					"01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-" + 
					"\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	private Usuario usuarioLogado = null;
	
	private String emailLogin = "";
	private String senhaLogin = "";
	
	@Inject
	private UsuarioDAO usuarioDao;
	@Inject
	private MecanismoDeHash mecanismoHash;
	
	//UI
	private UIComponent campoEmailLogin;
	
	public void doLogin() throws NoSuchAlgorithmException {
		if (emailLogin == null || emailLogin.isEmpty() ||
				senhaLogin == null || senhaLogin.isEmpty()) {
			
			return;
		}
		
		Usuario usuario = usuarioDao.getUsuario(emailLogin);
		
		if (usuario != null && Arrays.equals(usuario.getSenhaHasheada(), mecanismoHash.aplicarSHA256(senhaLogin.getBytes()))) {
			usuarioLogado = usuario;
		}
		
		else {
			FacesContext.getCurrentInstance().addMessage(campoEmailLogin.getClientId(), new FacesMessage() {
				@Override
				public Severity getSeverity() {
					return SEVERITY_ERROR;
				}
				
				@Override
				public String getSummary() {
					return "Usuário não encontrado!";
				}
			});
		}
	}
	
	public boolean usuarioEstaLogado() {
		return usuarioLogado != null;
	}
	
	public String finalizarSessao() {
		if (usuarioEstaLogado()) {
			usuarioLogado = null;
			
			return "loja?faces-redirect=true";
		}
		
		return null;
	}
	
	public void validarEmail(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(valor);
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage() {
				@Override
				public Severity getSeverity() {
					return SEVERITY_ERROR;
				}
				
				@Override
				public String getSummary() {
					return "E-mail inválido!";
				}
			});
		}
	}
	
	public void validatePassword(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		
		if (valor.length() < PASSWORD_MIN_SIZE) {
			throw new ValidatorException(new FacesMessage() {
				@Override
				public Severity getSeverity() {
					return SEVERITY_ERROR;
				}
				
				@Override
				public String getSummary() {
					return "Senha inválida! (+" + (PASSWORD_MIN_SIZE - valor.length()) + ")";
				}
			});
		}
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSenhaLogin() {
		return senhaLogin;
	}

	public void setSenhaLogin(String senhaLogin) {
		this.senhaLogin = senhaLogin;
	}

	public UIComponent getCampoEmailLogin() {
		return campoEmailLogin;
	}

	public void setCampoEmailLogin(UIComponent campoEmailLogin) {
		this.campoEmailLogin = campoEmailLogin;
	}
}
