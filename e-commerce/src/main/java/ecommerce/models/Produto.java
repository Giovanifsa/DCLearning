package ecommerce.models;

import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;

import java.math.BigDecimal;

import java.util.Date;
import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {
	/*
	 * Variável static utilizada para representar erro 404 de imagem (não encontrado)
	 * A imagem é utilizada para quando não há imagens adicionadas ao produto.
	 */
	@Transient
	public static final ArquivoRecurso IMAGEM_NAO_ENCONTRADA = new ArquivoRecurso("resources/images", "nao_encontrado.png");
	
	public static int quantidadeProdutoTotal;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private long codigoProduto;

	@NotNull
	private String nomeProduto;

	@Lob
	private String descricao;
	private BigDecimal precoDeVenda;
	private BigDecimal custoDeCompra;
	private int quantidade;
	private Date dataDeEntrada;
	private BigDecimal quantidadeProdutoTotal = new BigDecimal(0);
	
	
	
	//Valor variando de 0-100 (deve ser dividido por 100 para ter a porcentagem real)
	private BigDecimal margemDeLucroPorcentual = new BigDecimal(0);
	
	@OneToOne(fetch = FetchType.EAGER)
	private ArquivoRecurso imagemProduto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Loja lojaDoProduto;
	
	//Propriedade de vendas, com ela podemos organizar os produtos
	//pela quantia de vendas
	private int vendas = 0;
	
	/**
	 * O equals é um método que deve ser preferido ser chamado para verificar se dois objetos
	 * são iguals. Por padrão, as apis padrões do java que verificam se dois objetos
	 * são iguais utilizam o equals, como o ArrayList.contains, etc. Sobrecarreguei o equals
	 * dessa entidade para verificar se o codigo dela é igual o codigo de outra entidade.
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Produto && ((Produto)obj).codigoProduto == codigoProduto);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCodigo() {
		return codigoProduto;
	}

	public void setCodigo(long codigo) {
		this.codigoProduto = codigo;
	}

	public String getNome() {
		return nomeProduto;
	}

	public void setNome(String nome) {
		this.nomeProduto = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public BigDecimal getMargemDeLucroPorcentual() {
		return margemDeLucroPorcentual;
	}
	
	public boolean contemImagem() {
		return imagemProduto != null;
	}
	
	/**
	 * Calcula o preço final para o usuário (com a margem de lucro do vendedor).
	 * @return Preço final calculado.
	 */

	public BigDecimal calcularPrecoDeVenda(Produto produto) {
		return precoDeVenda = custoDeCompra.add(lojaDoProduto.getDespesaRateada()).
				multiply(new BigDecimal(1).add(margemDeLucroPorcentual).divide(new BigDecimal(100)));
	}

<<<<<<< HEAD
	
=======
	@SuppressWarnings("deprecation")
	public BigDecimal calcularPrecoFinal() {
		return preco.multiply(margemDeLucroPorcentual.divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	@SuppressWarnings("deprecation")
	public BigDecimal calcularPrecoFinal(int quantidade) {
		return preco.multiply(margemDeLucroPorcentual.divide(new BigDecimal(100))).multiply(new BigDecimal(quantidade)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
>>>>>>> origin/desenvolvimento

	public void setMargemDeLucroPorcentual(BigDecimal margemDeLucroPorcentual) {
		this.margemDeLucroPorcentual = margemDeLucroPorcentual;
	}
	
	/**
	 * Retorna a imagem do produto caso exista, ou retorna uma imagem padrão
	 * de arquivo não encontrado.
	 * @return
	 */
	public ArquivoRecurso getImagemPotencial() {
		if (imagemProduto != null) {
			return imagemProduto;
		}
		
		return IMAGEM_NAO_ENCONTRADA;
	}

	public ArquivoRecurso getImagemProduto() {
		return imagemProduto;
	}

	public void setImagemProduto(ArquivoRecurso imagemProduto) {
		this.imagemProduto = imagemProduto;
	}

	public Loja getLojaDoProduto() {
		return lojaDoProduto;
	}

	public void setLojaDoProduto(Loja lojaDoProduto) {
		this.lojaDoProduto = lojaDoProduto;
	}

	public int getVendas() {
		return vendas;
	}

	public void setVendas(int vendas) {
		this.vendas = vendas;
	}


	public BigDecimal getCusto() {
		return custoDeCompra;
	}

	public void setCusto(BigDecimal custo) {
		this.custoDeCompra = custo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getData() {
		return dataDeEntrada;
	}

	public void setData(Date data) {
		this.dataDeEntrada = data;
	}


	public BigDecimal getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(BigDecimal precoFinal) {
		this.precoDeVenda = precoFinal;
	}

	public BigDecimal getQuantidadeProdutoTotal() {
		return quantidadeProdutoTotal;
	}

	public void setQuantidadeProdutoTotal(BigDecimal quantidadeProdutoTotal) {
		this.quantidadeProdutoTotal = quantidadeProdutoTotal;
	}


}
