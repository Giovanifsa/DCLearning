package ecommerce.beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ecommerce.database.UserManager;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

@Named
@ViewScoped
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
	
	private String email = "";
	private String password = "";
	
	@Inject
	private SessionDataBean sessionBean;
	@Inject
	private UserManager userManager;
	@Inject
	private HashMechanism hashMechanism;
	
	//UI
	private UIComponent loginEmailField;
	
	public void doLogin() throws NoSuchAlgorithmException {
		if (email == null || email.isEmpty() ||
				password == null || password.isEmpty()) {
			
			return;
		}
		
		User user = userManager.findUser(email);
		
		if (user != null && Arrays.equals(user.getHashedPassword(), hashMechanism.defaultHashBytes(password.getBytes()))) {
			sessionBean.setCurrentUser(user);
		}
		
		else {
			FacesContext.getCurrentInstance().addMessage(loginEmailField.getClientId(), new FacesMessage() {
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
	
	public boolean isUserLogged() {
		return sessionBean.getCurrentUser() != null;
	}
	
	public String endSession() {
		if (isUserLogged()) {
			sessionBean.setCurrentUser(null);
			
			return "login?faces-redirect=true";
		}
		
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
		
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void validateEmail(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
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

	public UIComponent getLoginEmailField() {
		return loginEmailField;
	}

	public void setLoginEmailField(UIComponent loginEmailField) {
		this.loginEmailField = loginEmailField;
	}
}
