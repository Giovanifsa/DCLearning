package ecommerce.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private long codigo;

	@NotNull
	private String nome;

	@Lob
	private String descricao;
	private BigDecimal preco;
	private BigDecimal margemDeLucroPorcentual;
	private BigDecimal custo;
	
	
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
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getMargemDeLucroPorcentual() {
		return margemDeLucroPorcentual;
	}
	
	/**
	 * Calcula o preço final para o usuário (com a margem de lucro do vendedor).
	 * @return Preço final calculado.
	 */
	public BigDecimal calcularPrecoFinal() {
		return (preco.multiply(margemDeLucroPorcentual).add(preco));
	}

	public void setMargemDeLucroPorcentual(BigDecimal margemDeLucroPorcentual) {
		this.margemDeLucroPorcentual = margemDeLucroPorcentual;
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
}
