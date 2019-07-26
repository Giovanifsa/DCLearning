package ecommerce.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.Part;

import org.hibernate.exception.ConstraintViolationException;

import ecommerce.control.Transactional;
import ecommerce.daos.LojaDAO;
import ecommerce.daos.ProdutoDAO;
import ecommerce.models.ArquivoRecurso;
import ecommerce.models.Loja;
import ecommerce.models.Produto;

@Named
@ViewScoped
public class NovoProdutoBean implements Serializable {
	//Página novoProduto.xhtml
	private UIComponent campoCodigo;
	
	private int editandoId = -1;
	private Part imagemProduto;
	private ArquivoRecurso arquivoRecursoEditando;
	private String cadastroCodigoProduto;
	private String cadastroNomeProduto;
	private String cadastroDescricaoProduto;
	private String cadastroCustoCompra;
	private String cadastroMargemLucro;
	private Loja cadastroLoja;

	@Inject
	private ProdutoDAO dao;
	@Inject
	private LoginBean loginBean;
	@Inject
	private LojaDAO lojaDao;
	@Inject
	private TemplateBean pagTemplate;
	
	@Transactional
	public String salvarProduto() throws NumberFormatException, IOException {
		Produto produto = new Produto(cadastroNomeProduto, Long.valueOf(cadastroCodigoProduto),
			cadastroDescricaoProduto, new BigDecimal(cadastroCustoCompra), new BigDecimal(cadastroMargemLucro),
			null, cadastroLoja);
		
		try {
			if (editandoId == -1) {
				produto.setImagemProduto(dao.salvarImagemProduto(imagemProduto));
				dao.adicionarProduto(produto);
				
				pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto " + produto.getNome() + " adicionado com sucesso!", true);
			}
			
			else {
				if (imagemProduto != null) {
					produto.setImagemProduto(dao.salvarImagemProduto(imagemProduto));
				}
				
				else {
					produto.setImagemProduto(arquivoRecursoEditando);
				}
				
				produto.setId(editandoId);
				dao.atualizarProduto(produto);
				editandoId = -1;
				
				pagTemplate.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto " + produto.getNome() + " atualizado com sucesso!", true);
			}
			
			return "novoProduto?faces-redirect=true";
		} catch (PersistenceException ex) {
			if (ex.getCause() instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(campoCodigo.getClientId(), 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este código já está sendo usado!", null));
			}
		}
		
		return null;
	}

	// Esse método busca todos os produtos cadastrado para o usuário
	public List<Produto> getProdutos() {
		return dao.listarProdutosUsuario(loginBean.getUsuarioLogado());
	}
	
	public void editar(Produto produto) {
		editandoId = produto.getId();
		
		cadastroCodigoProduto = String.valueOf(produto.getCodigo());
		cadastroNomeProduto = produto.getNome();
		cadastroDescricaoProduto = produto.getDescricao();
		cadastroCustoCompra = produto.getCustoCompra().toString();
		cadastroMargemLucro = produto.getPorcentualMargemLucro().toString();
		cadastroLoja = produto.getLojaDoProduto();
		arquivoRecursoEditando = produto.getImagemProduto();
	}

	/*
	// Esse método deleta o produto
	@Transactional
	public void remover(Produto produto) {
		dao.removerProduto(produto);
	}*/
	
	public List<Loja> getLojas() {
		return lojaDao.listarLojasUsuario(loginBean.getUsuarioLogado());
	}

	public void validarCustoCompra(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		try {
			BigDecimal valor = new BigDecimal(value.toString());
			
			if (valor.compareTo(new BigDecimal(0)) <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException ex) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Custo de compra deve ser um valor de ponto flutuante maior que zero.", null));
		}
	}
	
	public void validarMargemLucro(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		try {
			new BigDecimal(value.toString());
		} catch (NumberFormatException ex) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Margem de lucro deve ser um valor de ponto flutuante.", null));
		}
	}
	
	public void validarCodigo(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		try {
			Integer.valueOf(value.toString());
		} catch (NumberFormatException ex) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "O código deve conter apenas números!", null));
		}
	}
	
	public void cancelarEdicao() {
		editandoId = -1;
		
		cadastroCodigoProduto = null;
		cadastroNomeProduto = null;
		cadastroDescricaoProduto = null;
		cadastroCustoCompra = null;
		cadastroMargemLucro = null;
		cadastroLoja = null;
		arquivoRecursoEditando = null;
		imagemProduto = null;
	}
	
	public boolean estaEditando() {
		return editandoId != -1;
	}
	
	public void validarImagem(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		if (value == null && editandoId == -1) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "É necessário uma imagem para o produto.", null));
		}
		
		else if (editandoId != -1) {
			return;
		}
		
		else {
			Part arquivo = (Part) value;
			
			ByteArrayOutputStream finalBytes = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			
			try {
				InputStream in = arquivo.getInputStream();
				int qntLido = 0;
				
				while ((qntLido = in.read(buffer)) != -1) {
					finalBytes.write(buffer, 0, qntLido);
					
					if (finalBytes.size() >= 1024 * 1024 * 10) {
						throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tamanho limite excedido! (10 MB)", null));
					}
				}
				
				String mime = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(finalBytes.toByteArray()));
				
				if (!mime.startsWith("image/")) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "O arquivo enviado não é uma imagem.", null));
				}
			} catch (IOException e) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar o arquivo.", null));
			}
		}
	}

	public Part getImagemProduto() {
		return imagemProduto;
	}

	public void setImagemProduto(Part imagemProduto) {
		this.imagemProduto = imagemProduto;
	}

	public String getCadastroCodigoProduto() {
		return cadastroCodigoProduto;
	}

	public void setCadastroCodigoProduto(String cadastroCodigoProduto) {
		this.cadastroCodigoProduto = cadastroCodigoProduto;
	}

	public String getCadastroNomeProduto() {
		return cadastroNomeProduto;
	}

	public void setCadastroNomeProduto(String cadastroNomeProduto) {
		this.cadastroNomeProduto = cadastroNomeProduto;
	}

	public String getCadastroDescricaoProduto() {
		return cadastroDescricaoProduto;
	}

	public void setCadastroDescricaoProduto(String cadastroDescricaoProduto) {
		this.cadastroDescricaoProduto = cadastroDescricaoProduto;
	}

	public String getCadastroCustoCompra() {
		return cadastroCustoCompra;
	}

	public void setCadastroCustoCompra(String cadastroCustoCompra) {
		this.cadastroCustoCompra = cadastroCustoCompra;
	}

	public String getCadastroMargemLucro() {
		return cadastroMargemLucro;
	}

	public void setCadastroMargemLucro(String cadastroMargemLucro) {
		this.cadastroMargemLucro = cadastroMargemLucro;
	}

	public Loja getCadastroLoja() {
		return cadastroLoja;
	}

	public void setCadastroLoja(Loja cadastroLoja) {
		this.cadastroLoja = cadastroLoja;
	}

	public UIComponent getCampoCodigo() {
		return campoCodigo;
	}

	public void setCampoCodigo(UIComponent campoCodigo) {
		this.campoCodigo = campoCodigo;
	}
}
