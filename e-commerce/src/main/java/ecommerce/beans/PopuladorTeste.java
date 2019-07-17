package ecommerce.beans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ecommerce.models.BinaryData;
import ecommerce.models.Product;
import ecommerce.models.ResourceFile;
import ecommerce.models.Store;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

public class PopuladorTeste {
	public static void main(String args[]) throws NoSuchAlgorithmException, IOException {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
		EntityManager em = emFactory.createEntityManager();
		
		User u = new User();
		u.setAdmin(false);
		u.setEmail("teste@teste.com");
		u.setHashedPassword(new HashMechanism().defaultHashBytes("1234567890".getBytes()));
		
		Store s = new Store();
		s.setCnpj("12345");
		s.setFantasyName("fantasia");
		s.setOwner(u);
		s.setTotalExpenses(24.0d);
		
		ResourceFile file = new ResourceFile();
		file.setFileName("foto_teste.png");
		file.setFilePath("foto_teste.png");
		
		Product p = new Product();
		p.setCode(12309321093921L);
		p.setDescription("23231312");
		p.setName("Produtinho");
		p.setPrice(47.2d);
		p.setProdImage(file);
		p.setProdStore(s);
		p.setProfitMarginPercentual(32.7d);
		p.setSells(24);
		
		em.getTransaction().begin();
		em.persist(u);
		em.persist(s);
		em.persist(file);
		em.persist(p);
		em.getTransaction().commit();
	}
}
