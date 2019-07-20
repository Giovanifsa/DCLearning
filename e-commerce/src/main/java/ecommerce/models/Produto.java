package ecommerce.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;

import javax.validation.constraints.NotNull;

@Entity
public class Produto {
	
	public static int quantidadeProdutoTotal;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private long codigo;

	@NotNull
	private String nome;

	@Lob
	private String descricao;
	private BigDecimal precoFinal;
	private BigDecimal margemDeLucroPorcentual;
	private BigDecimal custo;
	private int quantidade;
	private Date data;
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
	private ArquivoRecurso imagem;
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return precoFinal;
	}

	public void setPreco(BigDecimal preco) {
		this.precoFinal = preco;
	}

	public BigDecimal getMargemDeLucroPorcentual() {
		return margemDeLucroPorcentual;
	}
	
	/**
	 * Calcula o preço final para o usuário (com a margem de lucro do vendedor).
	 * @return Preço final calculado.
	 */
	public BigDecimal calcularPrecoFinal(Produto produto) {
		return (precoFinal.multiply(margemDeLucroPorcentual).add(precoFinal));
	}

	public void setMargemDeLucroPorcentual(BigDecimal margemDeLucroPorcentual) {
		this.margemDeLucroPorcentual = margemDeLucroPorcentual;
	}

	public ArquivoRecurso getImagemProduto() {
		return imagem;
	}

	public void setImagemProduto(ArquivoRecurso imagemProduto) {
		this.imagem = imagemProduto;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public static int getQuantidadeProdutoTotal() {
		return quantidadeProdutoTotal;
	}

	public static void setQuantidadeProdutoTotal(int quantidadeProdutoTotal) {
		Produto.quantidadeProdutoTotal = quantidadeProdutoTotal;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}

	public ArquivoRecurso getImagem() {
		return imagem;
	}

	public void setImagem(ArquivoRecurso imagem) {
		this.imagem = imagem;
	}
	
	
}
