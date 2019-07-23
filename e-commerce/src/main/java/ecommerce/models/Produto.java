package ecommerce.models;

import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;

import java.math.BigDecimal;
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
import javax.persistence.Transient;
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
	private BigDecimal custoCompra = new BigDecimal(0);
	private Date data;
	
	//Valor variando de 0-100 (deve ser dividido por 100 para ter a porcentagem real)
	private BigDecimal porcentualMargemLucro = new BigDecimal(0);
	
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
	
	public boolean contemImagem() {
		return imagemProduto != null;
	}

	public BigDecimal getCustoCompra() {
		return custoCompra;
	}

	public void setCustoCompra(BigDecimal custoCompra) {
		this.custoCompra = custoCompra;
	}
	
	public BigDecimal getPorcentualMargemLucro() {
		return porcentualMargemLucro;
	}

	public void setPorcentualMargemLucro(BigDecimal porcentualMargemLucro) {
		this.porcentualMargemLucro = porcentualMargemLucro;
	}

	public ArquivoRecurso getImagemProduto() {
		if (imagemProduto != null) {
			return imagemProduto;
		}
		
		return new ArquivoRecurso("resources/images", "nao_encontrado.png");
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal calcularPreco() {
				//(CustoCompra		+ DespesasRateadas=(DespesasTotais / Quantia de produtos da loja))
		return	(custoCompra.add(lojaDoProduto.getDespesasTotais().divide(new BigDecimal(lojaDoProduto.getQuantiaProdutos()))))
				//*						(1 + (MargemDeLucro 					/				100))
				.multiply(new BigDecimal(1).add(porcentualMargemLucro.divide(new BigDecimal(100))));
	}
	
	public BigDecimal calcularPrecoPelaQuantidade(int quantidade) {
		return calcularPreco().multiply(new BigDecimal(quantidade));
	}
}
