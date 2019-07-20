package ecommerce.beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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

import ecommerce.daos.ProdutoDAO;
import ecommerce.daos.UsuarioDAO;
import ecommerce.models.Produto;
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
	
	private String cadastroEmail;
	private String cadastroEmail2;
	private String cadastroSenha;
	private String cadastroSenha2;
	
	private String redirecionamento;
	
	@Inject
	private UsuarioDAO usuarioDao;
	@Inject
	private MecanismoDeHash mecanismoHash;
	@Inject
	private ProdutoDAO produtoDao;
	
	//UI
	private UIComponent campoEmailLogin;
	private UIComponent campoCadastroEmail;
	private UIComponent campoCadastroSenha;
	
	public String iniciarSessao() throws NoSuchAlgorithmException {
		if (emailLogin == null || emailLogin.isEmpty() ||
				senhaLogin == null || senhaLogin.isEmpty()) {
		}
		
		Usuario usuario = usuarioDao.procurarUsuario(emailLogin);
		
		if (usuario != null && Arrays.equals(usuario.getSenhaHasheada(), mecanismoHash.aplicarSHA256(senhaLogin.getBytes()))) {
			usuarioLogado = usuario;
			
			return processarRedirecionamento("loja?faces-redirect=true");
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
			
			emailLogin = "";
			senhaLogin = "";
		}
		
		return null;
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
	
	public void validarSenha(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
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
	
	public String getEmailLogin() {
		return emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
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
	
	public boolean deveMostrarRecentes() {
		return !produtoDao.buscarProdutosRecentes(1).isEmpty();
	}
	
	public List<Produto> getProdutosRecentes() {
		return produtoDao.buscarProdutosRecentes(10);
	}

	public String getCadastroEmail() {
		return cadastroEmail;
	}

	public void setCadastroEmail(String cadastroEmail) {
		this.cadastroEmail = cadastroEmail;
	}

	public String getCadastroEmail2() {
		return cadastroEmail2;
	}

	public void setCadastroEmail2(String cadastroEmail2) {
		this.cadastroEmail2 = cadastroEmail2;
	}

	public String getCadastroSenha() {
		return cadastroSenha;
	}

	public void setCadastroSenha(String cadastroSenha) {
		this.cadastroSenha = cadastroSenha;
	}

	public String getCadastroSenha2() {
		return cadastroSenha2;
	}

	public void setCadastroSenha2(String cadastroSenha2) {
		this.cadastroSenha2 = cadastroSenha2;
	}
	
	public UIComponent getCampoCadastroEmail() {
		return campoCadastroEmail;
	}

	public void setCampoCadastroEmail(UIComponent campoCadastroEmail) {
		this.campoCadastroEmail = campoCadastroEmail;
	}

	public UIComponent getCampoCadastroSenha() {
		return campoCadastroSenha;
	}

	public void setCampoCadastroSenha(UIComponent campoCadastroSenha) {
		this.campoCadastroSenha = campoCadastroSenha;
	}

	public void finalizarCadastro() throws NoSuchAlgorithmException {
		boolean invalido = false;
		
		if (!cadastroEmail.equals(cadastroEmail2)) {
			FacesContext.getCurrentInstance().addMessage(campoCadastroEmail.getClientId(), new FacesMessage() {
				@Override
				public Severity getSeverity() {
					return SEVERITY_ERROR;
				}
				
				@Override
				public String getSummary() {
					return "Ambos campos de e-mail devem ser iguais!";
				}
			});
			
			invalido = true;
		}
		
		if (!cadastroSenha.equals(cadastroSenha2)) {
			FacesContext.getCurrentInstance().addMessage(campoCadastroSenha.getClientId(), new FacesMessage() {
				@Override
				public Severity getSeverity() {
					return SEVERITY_ERROR;
				}
				
				@Override
				public String getSummary() {
					return "Ambos campos de senha devem ser iguais!";
				}
			});
			
			invalido = true;
		}
		
		if (!invalido) {
			usuarioLogado = usuarioDao.adicionarUsuario(cadastroEmail, cadastroSenha);
			
			limparCampos();
		}
	}
	
	private String processarRedirecionamento(String padrao) {
		if (redirecionamento != null) {
			return redirecionamento + "?faces-redirect=true";
		}
		
		return padrao;
	}
	
	private void limparCampos() {
		cadastroEmail = null;
		cadastroEmail2 = null;
		cadastroSenha = null;
		cadastroSenha2 = null;
	}

	public String getRedirecionamento() {
		return redirecionamento;
	}

	public void setRedirecionamento(String redirecionamento) {
		this.redirecionamento = redirecionamento;
	}
}
