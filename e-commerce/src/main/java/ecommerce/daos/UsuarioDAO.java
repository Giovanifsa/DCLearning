package ecommerce.daos;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ecommerce.control.Transactional;
import ecommerce.models.Usuario;
import ecommerce.tools.MecanismoDeHash;

@Named
public class UsuarioDAO implements Serializable {
	@Inject
	private EntityManager em;
	
	@Inject
	private MecanismoDeHash hasher;
	
	/**
	 * Procura um usuário por seu email.
	 * @param email Email do usuário.
	 * @return Usuario ou null caso não encontrado.
	 */
	public Usuario procurarUsuario(String email) {
		TypedQuery<Usuario> t = em.createQuery("SELECT u FROM " + Usuario.class.getSimpleName() + " u WHERE u.email = :email", Usuario.class);
		t.setParameter("email", email);
		
		try {
			return t.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	/**
	 * Cria um novo usuário a partir de email e senha.
	 * @param email
	 * @param password
	 * @param cadastroSenha 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional
	public Usuario adicionarUsuario(String email, String password) throws NoSuchAlgorithmException {
		Usuario u = new Usuario(email, hasher.aplicarSHA256(password.getBytes()));
		em.persist(u);
		
		return u;
	}
}
