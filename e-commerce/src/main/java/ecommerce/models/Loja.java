	package ecommerce.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
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
	
	@Column(unique = true)
	private String cnpj;
	
	private int quantiaProdutos;
	private String nomeFantasia;
	private BigDecimal despesasTotais;
	
	public Loja() {}
	
	public Loja(Usuario dono, String nomeFantasia, String cnpj, BigDecimal despesasTotais) {
		this(nomeFantasia, cnpj, despesasTotais);
		this.dono = dono;
	}
	
	public Loja(String nomeFantasia, String cnpj, BigDecimal despesasTotais) {
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.despesasTotais = despesasTotais;
	}
	
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
	
	public BigDecimal calcularDespesaRateada() {
		if (quantiaProdutos <= 0) {
			return despesasTotais;
		}
		
		return despesasTotais.divide(new BigDecimal(quantiaProdutos));
	}
}
