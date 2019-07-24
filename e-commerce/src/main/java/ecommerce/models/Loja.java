	package ecommerce.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")
@Entity
public class Loja implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario dono;
	
	private String cnpj;
	
	private int quantiaProdutos;
	private String nomeFantasia;
	private BigDecimal despesasTotais = new BigDecimal(1);
	
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getDono() {
		return dono;
	}

	public int getQuantiaProdutos() {
		return quantiaProdutos;
	}

	public void setQuantiaProdutos(int quantiaProdutos) {
		this.quantiaProdutos = quantiaProdutos;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public BigDecimal getDespesasTotais() {
		return despesasTotais;
	}

	public void setDespesasTotais(BigDecimal despesasTotais) {
		this.despesasTotais = despesasTotais;
	}
	
	@SuppressWarnings("null")
	public BigDecimal calcularDespesaTotal() {
		Produto[] produtos = null;
		for (Produto produto : produtos) {
			
			despesasTotais	= despesasTotais.add(produto.getCustoCompra());
		}
		return despesasTotais;
	}
	
	@SuppressWarnings("null")
	public BigDecimal calcularDespesaRateada() {
		return despesasTotais.divide(new BigDecimal(quantiaProdutos));
	}
}
