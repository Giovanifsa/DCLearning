package ecommerce.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ecommerce.daos.ProductDao;
import ecommerce.models.Product;
import ecommerce.models.ResourceFile;
import ecommerce.models.Store;
import ecommerce.models.User;
import ecommerce.tools.HashMechanism;

public class PopulaBanco {

	@Inject
	EntityManager em;
	
	@Inject
	ProductDao dao;
	public static void main(String args[]) throws NoSuchAlgorithmException, IOException {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
		EntityManager em = emFactory.createEntityManager();
		
		User u0 = new User();
		u0.setAdmin(false);
		u0.setEmail("teste0@teste.com");
		u0.setHashedPassword(new HashMechanism().defaultHashBytes("0000000000000".getBytes()));
		
		User u1 = new User();
		u1.setAdmin(false);
		u1.setEmail("teste1@teste.com");
		u1.setHashedPassword(new HashMechanism().defaultHashBytes("1111111111111".getBytes()));
		
		User u2 = new User();
		u2.setAdmin(false);
		u2.setEmail("teste2@teste.com");
		u2.setHashedPassword(new HashMechanism().defaultHashBytes("22222222222222".getBytes()));
		
		User u3 = new User();
		u3.setAdmin(false);
		u3.setEmail("teste3@teste.com");
		u3.setHashedPassword(new HashMechanism().defaultHashBytes("33333333333333".getBytes()));
		
		
		Store s0 = new Store();
		s0.setCnpj("00000000000-1");
		s0.setFantasyName("Empresa 00");
		s0.setOwner(u0);
		s0.setTotalExpenses(24.0d);
		
		Store s1 = new Store();
		s1.setCnpj("1111111111-2");
		s1.setFantasyName("Empresa 01");
		s1.setOwner(u1);
		s1.setTotalExpenses(25.0d);
		
		Store s2 = new Store();
		s2.setCnpj("222222222-3");
		s2.setFantasyName("Empresa 02");
		s2.setOwner(u2);
		s2.setTotalExpenses(24.0d);

		Store s3 = new Store();
		s3.setCnpj("3333333333-4");
		s3.setFantasyName("Empresa 03");
		s3.setOwner(u3);
		s3.setTotalExpenses(24.0d);
		
		ResourceFile file0 = new ResourceFile();
		file0.setFileName("capita.png");
		file0.setFilePath("resource/images");
		
		ResourceFile file1 = new ResourceFile();
		file1.setFileName("formiga.png");
		file1.setFilePath("resource/images");
		
		ResourceFile file2 = new ResourceFile();
		file2.setFileName("guardioes.png");
		file2.setFilePath("resource/images");
		
		ResourceFile file3 = new ResourceFile();
		file3.setFileName("hulk.png");
		file3.setFilePath("resource/images");
		
		Product p0 = new Product();
		p0.setCode(0000000001L);
		p0.setDescription("Filme da Capitã Marvel");
		p0.setName("Capitã Marvel");
		p0.setPrice(new BigDecimal(5.9));
		p0.setProdImage(file0);
		p0.setProdStore(s0);
		p0.setProfitMarginPercentual(new BigDecimal(2.20));
		p0.setSells(50);
		
		Product p1 = new Product();
		p1.setCode(1111111112L);
		p1.setDescription("Filme do Homem Formiga");
		p1.setName("Homem Formiga");
		p1.setPrice(new BigDecimal(6.9));
		p1.setProdImage(file1);
		p1.setProdStore(s1);
		p1.setProfitMarginPercentual(new BigDecimal(2.20));
		p1.setSells(40);
		
		Product p2 = new Product();
		p2.setCode(222222222223L);
		p2.setDescription("Filme dos Guardioes da Galaxia");
		p2.setName("Guardioes da Galaxia");
		p2.setPrice(new BigDecimal(5.9));
		p2.setProdImage(file2);
		p2.setProdStore(s2);
		p2.setProfitMarginPercentual(new BigDecimal(2.20));
		p2.setSells(50);
		
		Product p3 = new Product();
		p3.setCode(333333333334L);
		p3.setDescription("Filme do Hulk");
		p3.setName("O Incrivel Hulk");
		p3.setPrice(new BigDecimal(3.9));
		p3.setProdImage(file3);
		p3.setProdStore(s3);
		p3.setProfitMarginPercentual(new BigDecimal(2.20));
		p3.setSells(50);
		
		em.getTransaction().begin();
		em.persist(u0);
		em.persist(s0);
		em.persist(file0);
		em.persist(p0);
		
		em.persist(u1);
		em.persist(s1);
		em.persist(file1);
		em.persist(p1);
		
		em.persist(u2);
		em.persist(s2);
		em.persist(file2);
		em.persist(p2);
		
		em.persist(u3);
		em.persist(s3);
		em.persist(file3);
		em.persist(p3);
		
		em.getTransaction().commit();
	}
}

