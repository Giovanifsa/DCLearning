package ecommerce.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.binary.Base32;

import ecommerce.daos.ProdutoDAO;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class BuscarProdutosBean implements Serializable {
	//ViewParam
	private String b32Buscando;
	private List<Produto> produtosEncontrados;
	
	@Inject
	private TemplateBean pagTemplate;
	
	@Inject
	private ProdutoDAO produtoDao;

	public String getB32Buscando() {
		return b32Buscando;
	}
	
	public List<Produto> getProdutosEncontrados() {
		return produtosEncontrados;
	}

	public void setB32Buscando(String b32Buscando) {
		this.b32Buscando = b32Buscando;
		
		Base32 base32 = new Base32();
		pagTemplate.setNavbarTextoPesquisa(new String(base32.decode(b32Buscando)));
	}
	
	public void processarBusca() {
		if (!buscaGerouResultados()) {
			Base32 base32 = new Base32();
			produtosEncontrados = produtoDao.procurarPorConteudoNome(new String(base32.decode(b32Buscando)));
		}
	}
	
	public boolean buscaGerouResultados() {
		return produtosEncontrados != null && !produtosEncontrados.isEmpty();
	}
	
	public String processarLookPainel() {
		return buscaGerouResultados() ? "info" : "danger";
	}
}
