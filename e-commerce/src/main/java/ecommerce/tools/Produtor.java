package ecommerce.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe cujo CDI chamará para instanciar objetos que ele utilizará.
 * Por padrão, é utilizada para gerar interfaces implementadas
 * (interfaces não podem ser instanciadas com new), um exemplo é 
 * o EntityManager, é uma interface implementada por um objeto do hibernate.
 * 
 * @author Giovani
 *
 */
public class Produtor implements Serializable {
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
