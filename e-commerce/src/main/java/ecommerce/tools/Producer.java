package ecommerce.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe que produz objetos para injeção do CDI
 * @author Giovani
 *
 */
public class Producer implements Serializable {
	private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
	
	/**
	 * Produz um EntityManager para injeção.
	 * @return EntityManager.
	 */
	@Produces
	@RequestScoped
	public EntityManager produceEntityManager() {
		return emFactory.createEntityManager();
	}
}
