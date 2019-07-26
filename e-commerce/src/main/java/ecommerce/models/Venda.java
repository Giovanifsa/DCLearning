package ecommerce.models;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVenda;

	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario cliente;

	private BigDecimal valor;

	@OneToMany(fetch = FetchType.EAGER)
	private List<ItemCarrinho> produtosComprados;

	public List<ItemCarrinho> getProdutosComprados() {
		return produtosComprados;
	}

	public void setProdutosComprados(List<ItemCarrinho> produtosComprados) {
		this.produtosComprados = produtosComprados;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public String getStringData() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		return formatter.format(dataVenda);
	}
}
