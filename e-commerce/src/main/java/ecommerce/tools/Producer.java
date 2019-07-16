package ecommerce.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Producer implements Serializable {
	private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
	
	@Produces
	@RequestScoped
	public EntityManager produceEntityManager() {
		return emFactory.createEntityManager();
	}
}
