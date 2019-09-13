package ecommerce.models;

import java.util.Date;

public class ItemVendido {
	private Date dataVenda;
	private ItemCarrinho item;
	private Usuario usuarioCompra;
	
	public Date getDataVenda() {
		return dataVenda;
	}
	
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public ItemCarrinho getItem() {
		return item;
	}
	
	public void setItem(ItemCarrinho item) {
		this.item = item;
	}
	
	public Usuario getUsuarioCompra() {
		return usuarioCompra;
	}
	
	public void setUsuarioCompra(Usuario usuarioCompra) {
		this.usuarioCompra = usuarioCompra;
	}
}
