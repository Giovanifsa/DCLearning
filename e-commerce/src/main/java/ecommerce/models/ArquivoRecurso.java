package ecommerce.models;

import javax.persistence.Entity;

/**
 * Representa um resource do site, como uma imagem ou um arquivo qualquer.
 * Por exemplo: nomeArquivo = livro_harrypotter.png e nomeDiretorio = imagensProdutos
 * O diretório completo deve ser: /nomeDiretorio/nomeArquivo, exemplo:
 * /imagensProdutos/livro_harrypotter.png, localhost:8080/e-commerce/imagensProdutos/livro_harrypotter.png
 * 
 * @author Giovani
 *
 */
@Entity
public class ArquivoRecurso {
	private int id;
	
	private String nomeArquivo;
	private String nomeDiretorio;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getNomeDiretorio() {
		return nomeDiretorio;
	}
	
	public void setNomeDiretorio(String nomeDiretorio) {
		this.nomeDiretorio = nomeDiretorio;
	}
}
