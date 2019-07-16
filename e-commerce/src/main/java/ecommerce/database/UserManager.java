package ecommerce.database;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ecommerce.control.Transactional;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

@Named
public class UserManager implements Serializable {
	@Inject
	private EntityManager em;
	
	@Inject
	private HashMechanism hasher;
	
	public User findUser(String email) {
		TypedQuery<User> t = em.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.email = :email", User.class);
		t.setParameter("email", email);
		
		try {
			return t.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Transactional
	public User createUser(String email, String password) throws NoSuchAlgorithmException {
		User u = new User(email, hasher.defaultHashBytes(password.getBytes()));
		em.persist(u);
		
		return u;
	}
}
