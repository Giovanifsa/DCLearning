package ecommerce.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import ecommerce.models.Produto;
import ecommerce.models.Usuario;
import ecommerce.tools.MecanismoDeHash;

@Named
public class PopulaBanco {

	@Inject
	EntityManager em;
	
	@Inject
	ProdutoDAO dao;
	@Inject 
	VendasBean vendas;
	
	public static void main(String args[]) throws NoSuchAlgorithmException, IOException {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ecommerce");
		EntityManager em = emFactory.createEntityManager();

		
		Usuario u0 = new Usuario();
		u0.setNome("João0");
		u0.setEmail("teste0@0teste.com");
		u0.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("0000000000".getBytes()));
		
		Usuario u1 = new Usuario();
		u1.setNome("João1");
		u1.setEmail("teste1@1teste.com");
		u1.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("111111111".getBytes()));
		
		Usuario u2 = new Usuario();
		u2.setNome("João2");
		u2.setEmail("teste2@2teste.com");
		u2.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("22222222".getBytes()));
		
		Usuario u3 = new Usuario();
		u3.setNome("João3");
		u3.setEmail("teste3@3teste.com");
		u3.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("3333333333".getBytes()));
		
		Usuario u4 = new Usuario();
		u4.setNome("João4");
		u4.setEmail("teste4@0teste.com");
		u4.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("444444444".getBytes()));
		
		Usuario u5 = new Usuario();
		u5.setNome("João5");
		u5.setEmail("teste5@1teste.com");
		u5.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("555555555".getBytes()));
		
		Usuario u6 = new Usuario();
		u6.setNome("João6");
		u6.setEmail("teste6@2teste.com");
		u6.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("6666666666".getBytes()));
		
		Usuario u7 = new Usuario();
		u7.setNome("João7");
		u7.setEmail("teste173@3teste.com");
		u7.setSenhaHasheada(new MecanismoDeHash().aplicarSHA256("77777777777".getBytes()));
		
		
		Loja s0 = new Loja();
		s0.setCnpj("121212");
		s0.setNomeFantasia("Empresa 240");
		s0.setDono(u0);
		s0.setDespesasTotais(new BigDecimal(1000));
		
		Loja s1 = new Loja();
		s1.setCnpj("232323-2");
		s1.setNomeFantasia("Empresa 2451");
		s1.setDono(u1);
		s1.setDespesasTotais(new BigDecimal(1000));
		
		Loja s2 = new Loja();
		s2.setCnpj("34343434-3");
		s2.setNomeFantasia("Empresa 12");
		s2.setDono(u2);
		s2.setDespesasTotais(new BigDecimal(1000));

		Loja s3 = new Loja();
		s3.setCnpj("4545454545-4");
		s3.setNomeFantasia("Empresa 113");
		s3.setDono(u3);
		s3.setDespesasTotais(new BigDecimal(1000));
		
		Loja s4 = new Loja();
		s4.setCnpj("67676767-1");
		s4.setNomeFantasia("Empresa 440");
		s4.setDono(u4);
		s4.setDespesasTotais(new BigDecimal(1000));
		
		Loja s5 = new Loja();
		s5.setCnpj("787878787-2");
		s5.setNomeFantasia("Empresa 5451");
		s5.setDono(u5);
		s5.setDespesasTotais(new BigDecimal(1000));
		
		Loja s6 = new Loja();
		s6.setCnpj("8989898989-3");
		s6.setNomeFantasia("Empresa 62");
		s6.setDono(u6);
		s6.setDespesasTotais(new BigDecimal(1000));
		
		Loja s7 = new Loja();
		s7.setCnpj("331723333-4");
		s7.setNomeFantasia("Empresa 117");
		s7.setDono(u7);
		s7.setDespesasTotais(new BigDecimal(1000));
		
		ArquivoRecurso file0 = new ArquivoRecurso();
		file0.setNomeArquivo("capita.png");
		
		ArquivoRecurso file1 = new ArquivoRecurso();
		file1.setNomeArquivo("formiga.png");
		
		ArquivoRecurso file2 = new ArquivoRecurso();
		file2.setNomeArquivo("guardioes.png");
		
		ArquivoRecurso file3 = new ArquivoRecurso();
		file3.setNomeArquivo("hulk.png");
		
		ArquivoRecurso file4 = new ArquivoRecurso();
		file4.setNomeArquivo("capita.png");
		
		ArquivoRecurso file5 = new ArquivoRecurso();
		file5.setNomeArquivo("formiga.png");
		
		ArquivoRecurso file6 = new ArquivoRecurso();
		file6.setNomeArquivo("guardioes.png");
		
		ArquivoRecurso file7 = new ArquivoRecurso();
		file7.setNomeArquivo("hulk.png");
		
		Usuario u = new Usuario();
		Loja l = new Loja();
		ArquivoRecurso ar = new ArquivoRecurso();
		Produto p = new Produto();
		
		
		Produto p0 = new Produto();
		p0.setCodigo(12121212);
		p0.setDescricao("Primeiro");
		p0.setNome("Descriçao primeiro");
		p0.setCustoCompra(new BigDecimal(2));
		p0.setImagemProduto(file0);
		p0.setLojaDoProduto(s0);
		p0.setPorcentualMargemLucro(new BigDecimal(200));
		p0.setVendas(50);
		
		Produto p1 = new Produto();
		p1.setCodigo(232323232L);
		p1.setDescricao("Segundo");
		p1.setNome("Descriçao segundo");
		p1.setCustoCompra(new BigDecimal(3));
		p1.setImagemProduto(file1);
		p1.setLojaDoProduto(s1);
		p1.setPorcentualMargemLucro(new BigDecimal(200));
		p1.setVendas(40);
		
		Produto p2 = new Produto();
		p2.setCodigo(34343434L);
		p2.setDescricao("Descriçao terceiro");
		p2.setNome("Terceiro");
		p2.setCustoCompra(new BigDecimal(5.9));
		p2.setImagemProduto(file2);
		p2.setLojaDoProduto(s2);
		p2.setPorcentualMargemLucro(new BigDecimal(200));
		p2.setVendas(50);
		
		u.setEmail("willian@willian.com");
		u.setNome("Willian");
		u.setSenhaHasheada("197103".getBytes());
		l.setCnpj("123456789-1");
		l.setDespesasTotais(new BigDecimal(1000));
		l.setDono(u);
		l.setNomeFantasia("Fantasia");
		l.setQuantiaProdutos(2000);
		ar.setNomeArquivo("hulk.png");
		ar.setNomeDiretorio("trash");
		p.setCodigo(123);
		p.setCustoCompra(new BigDecimal(100));
		p.setData(new Date(21/21/2011));
		p.setDescricao("Esse produto tem uma descrição bem grande, para verificar o que"
				+ " acontece com o layout da tela principal quando é adicionado um "
				+ "produto com uma descrição muito grande, se vai quebrar a formatação dos outros");
		p.setImagemProduto(ar);
		p.setLojaDoProduto(l);
		p.setNome("Um produto com um nome um pouco grande");
		p.setPorcentualMargemLucro(new BigDecimal(100));
		p.setVendas(20);
		
		Produto p3 = new Produto();
		p3.setCodigo(45454545L);
		p3.setDescricao("Descriçao quarto");
		p3.setNome("Quarto");
		p3.setCustoCompra(new BigDecimal(3.9));
		p3.setImagemProduto(file3);
		p3.setLojaDoProduto(s3);
		p3.setPorcentualMargemLucro(new BigDecimal(200));
		p3.setVendas(50);
		
		Produto p4 = new Produto();
		p4.setCodigo(5656565656L);
		p4.setDescricao("Descriçao quinto");
		p4.setNome("Quinto");
		p4.setCustoCompra(new BigDecimal(50));
		p4.setImagemProduto(file4);
		p4.setLojaDoProduto(s4);
		p4.setPorcentualMargemLucro(new BigDecimal(200));
		p4.setVendas(50);
		
		Produto p5 = new Produto();
		p5.setCodigo(66767673L);
		p5.setDescricao("Descriçao sexto");
		p5.setNome("Sexto");
		p5.setCustoCompra(new BigDecimal(50));
		p5.setImagemProduto(file5);
		p5.setLojaDoProduto(s5);
		p5.setPorcentualMargemLucro(new BigDecimal(200));
		p5.setVendas(40);
		
		Produto p6 = new Produto();
		p6.setCodigo(557835321L);
		p6.setDescricao("Descriçao setimo");
		p6.setNome("Setimo");
		p6.setCustoCompra(new BigDecimal(59));
		p6.setImagemProduto(file6);
		p6.setLojaDoProduto(s6);
		p6.setPorcentualMargemLucro(new BigDecimal(200));
		p6.setVendas(50);
		
		Produto p7 = new Produto();
		p7.setCodigo(324524L);
		p7.setDescricao("Descriçao oitavo");
		p7.setNome("Otavio");
		p7.setCustoCompra(new BigDecimal(70));
		p7.setImagemProduto(file7);
		p7.setLojaDoProduto(s7);
		p7.setPorcentualMargemLucro(new BigDecimal(220));
		p7.setVendas(50);
		
		em.getTransaction().begin();
		
		em.persist(u);
		em.persist(l);
		em.persist(ar);
		em.persist(p);
		
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
		
		em.persist(u4);
		em.persist(s4);
		em.persist(file4);
		em.persist(p4);
		
		em.persist(u5);
		em.persist(s5);
		em.persist(file5);
		em.persist(p5);
		
		em.persist(u6);
		em.persist(s6);
		em.persist(file6);
		em.persist(p6);
		
		em.persist(u7);
		em.persist(s7);
		em.persist(file7);
		em.persist(p7);
		
		em.getTransaction().commit();
		
	}
}

