package ecommerce.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;
import ecommerce.tools.MecanismoDeHash;

public class PopulaBanco {

	@Inject
	EntityManager em;
	
	@Inject
	ProdutoDAO dao;
	
	public static void main(String args[]) throws NoSuchAlgorithmException, IOException {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
		EntityManager em = emFactory.createEntityManager();
		
		Usuario u0 = new Usuario();
		u0.setAdmin(false);
		u0.setEmail("teste012@0teste.com");
		u0.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("11544532353".getBytes()));

		Usuario u1 = new Usuario();
		u1.setAdmin(false);
		u1.setEmail("teste121@1teste.com");
		u1.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("53434534".getBytes()));
		
		Usuario u2 = new Usuario();
		u2.setAdmin(false);
		u2.setEmail("teste212@2teste.com");
		u2.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("534345345".getBytes()));
		
		Usuario u3 = new Usuario();
		u3.setAdmin(false);
		u3.setEmail("teste123@3teste.com");
		u3.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("3545345453".getBytes()));
		
		
		Loja s0 = new Loja();
		s0.setCnpj("512345-1");
		s0.setNomeFantasia("Empresa 240");
		s0.setDono(u0);
		s0.setDespesasTotais(new BigDecimal(2155));
		
		Loja s1 = new Loja();
		s1.setCnpj("53455433-2");
		s1.setNomeFantasia("Empresa 2451");
		s1.setDono(u1);
		s1.setDespesasTotais(new BigDecimal(1200));
		
		Loja s2 = new Loja();
		s2.setCnpj("22122122222-3");
		s2.setNomeFantasia("Empresa 12");
		s2.setDono(u2);
		s2.setDespesasTotais(new BigDecimal(1150));

		Loja s3 = new Loja();
		s3.setCnpj("331333123333-4");
		s3.setNomeFantasia("Empresa 113");
		s3.setDono(u3);
		s3.setDespesasTotais(new BigDecimal(160));
		
		ArquivoRecurso file0 = new ArquivoRecurso();
		file0.setNomeArquivo("capitaa_marvel.png");
		file0.setNomeDiretorio("imagens");
		
		ArquivoRecurso file1 = new ArquivoRecurso();
		file1.setNomeArquivo("formiga_man.png");
		file1.setNomeDiretorio("imagens");
		
		ArquivoRecurso file2 = new ArquivoRecurso();
		file2.setNomeArquivo("guardioes.png");
		file2.setNomeDiretorio("imagens");
		
		ArquivoRecurso file3 = new ArquivoRecurso();
		file3.setNomeArquivo("hulk.png");
		file3.setNomeDiretorio("imagens");
		
		Produto p0 = new Produto();
		p0.setCodigo(0034534L);
		p0.setDescricao("Na década de 1960, a NASA planeja realizar uma das missões "
				+ "mais arriscadas de todos os tempos: fazer o homem pisar na Lua pela "
				+ "primeira vez. Durante a preparação, o astronauta Neil Armstrong "
				+ "enfrenta falhas e tragédias pessoais, o que torna sua jornada histórica "
				+ "até a Lua ainda mais desafiadora. Vencedor do Oscar de Melhores Efeitos "
				+ "Visuais.");
		p0.setNome("O Primeiro homem");
		p0.setPreco(new BigDecimal(30.0));
		p0.setImagemProduto(file0);
		p0.setLojaDoProduto(s0);
		p0.setMargemDeLucroPorcentual(new BigDecimal(2.20));
		p0.setVendas(50);
		p0.setCusto(new BigDecimal(13));
		Date data = new Date(11/11/2011);
		p0.setData(data);
		
		Produto p1 = new Produto();
		p1.setCodigo(145342L);
		p1.setDescricao("Filme do Homem Formiga");
		p1.setNome("Homem Formiga");
		p1.setPreco(new BigDecimal(20));
		p1.setImagemProduto(file1);
		p1.setLojaDoProduto(s1);
		p1.setMargemDeLucroPorcentual(new BigDecimal(2.20));
		p1.setVendas(40);
		Date data1 = new Date(11/11/2011);
		p1.setData(data1);
		
		Produto p2 = new Produto();
		p2.setCodigo(2453542223L);
		p2.setDescricao("Filme dos Guardioes da Galaxia");
		p2.setNome("Guardioes da Galaxia");
		p2.setPreco(new BigDecimal(5.9));
		p2.setImagemProduto(file2);
		p2.setLojaDoProduto(s2);
		p2.setMargemDeLucroPorcentual(new BigDecimal(2.20));
		p2.setVendas(50);
		Date data2 = new Date(11/11/2011);
		p2.setData(data2);
		
		Produto p3 = new Produto();
		p3.setCodigo(345345334L);
		p3.setDescricao("Filme do Hulk");
		p3.setNome("O Incrivel Hulk");
		p3.setPreco(new BigDecimal(3.9));
		p3.setImagemProduto(file3);
		p3.setLojaDoProduto(s3);
		p3.setMargemDeLucroPorcentual(new BigDecimal(2.20));
		p3.setVendas(50);
		Date data3 = new Date(11/11/2011);
		p3.setData(data3);
		
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

