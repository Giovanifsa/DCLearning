package ecommerce.models;

import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private long codigo;

	@NotNull
	private String nomeProduto;

	@Lob
	private String descricao;
	private BigDecimal precoDeVenda = new BigDecimal(0);
	private BigDecimal custo = new BigDecimal(0);
	private int quantidade;
	@Temporal(TemporalType.DATE)
	private Calendar data = Calendar.getInstance();
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
		
		return (obj != null && obj instanceof Produto && ((Produto)obj).codigo == codigo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nome) {
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

	public BigDecimal calcularPrecoDeVenda(Produto produto, Loja lojaDoProduto) {
		BigDecimal preco = new BigDecimal(0);
	preco = produto.getCusto().add(lojaDoProduto.getDespesaRateada()).
		multiply(new BigDecimal(1).add(produto.getMargemDeLucroPorcentual()).divide(new BigDecimal(100)));
			
			this.precoDeVenda = produto.getCusto().add(preco);
			
			return this.precoDeVenda;
	}


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
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Calendar getData() {
		return data;
	}

	public Calendar setData(Calendar data2) {
		return this.data = data2;
	}


	public BigDecimal getPrecoDeVenda() {
		return precoDeVenda;
	}

	public BigDecimal setPrecoDeVenda(BigDecimal precoFinal) {
		return this.precoDeVenda = precoFinal;
	}

	public BigDecimal getQuantidadeProdutoTotal() {
		return quantidadeProdutoTotal;
	}

	public void setQuantidadeProdutoTotal(BigDecimal quantidadeProdutoTotal) {
		this.quantidadeProdutoTotal = quantidadeProdutoTotal;
	}


}
