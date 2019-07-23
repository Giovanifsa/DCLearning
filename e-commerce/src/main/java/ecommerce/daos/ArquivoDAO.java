package ecommerce.daos;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.models.ArquivoRecurso;

@Named
public class ArquivoDAO implements Serializable {
	private static final Path DIRETORIO = Paths.get(System.getProperty("user.home") + File.separator + "ArquivosECommerce");
	private final SecureRandom geradorAleatorio;
	
	@Inject
	private EntityManager em;
	
	public ArquivoDAO() {
		geradorAleatorio = new SecureRandom();
	}
	
	private String gerarNomeAleatorio(int caracteres) {
		String str = "";
		
		for (int x = 0; x < caracteres; x++) {
			if ((geradorAleatorio.nextInt() % 2) == 0) {
				str += (char) (97 + geradorAleatorio.nextInt(26));
			}
			
			else {
				str += (char) (48 + geradorAleatorio.nextInt(10));
			}
		}
		
		return str;
	}
	
	/**
	 * Salva um caminho para um arquivo no disco local no banco de dados, e 
	 * grava o arquivo na pasta do usuário.
	 * @param nomeArquivo Nome do arquivo a ser salvo.
	 * @param nomeDiretorio Diretório que será salvo na pasta do usuário.
	 * @param dadosArquivo Bytes do arquivo que serão salvos.
	 * @return ArquivoRecurso denotando o nome gerado para o arquivo e o diretório salvo.
	 * @throws IOException
	 */
	public ArquivoRecurso salvarArquivo(String nomeArquivo, String nomeDiretorio, byte[] dadosArquivo) throws IOException {
		Path dir = construirCaminho(DIRETORIO.toString(), nomeDiretorio);
		Files.createDirectories(dir);
		
		String extensao = "";
		int indice = nomeArquivo.lastIndexOf(".");
		
		if (indice >= 0) {
			extensao = nomeArquivo.substring(indice);
		}
		
		Path arquivoPath;
		String nomeFinalArquivo;
		
		do {
			nomeFinalArquivo = gerarNomeAleatorio(32) + extensao;
			arquivoPath = construirCaminho(dir.toString(), nomeFinalArquivo);
		} while (Files.exists(arquivoPath));
			
		Files.write(arquivoPath, dadosArquivo, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		
		ArquivoRecurso recurso = new ArquivoRecurso(nomeDiretorio, nomeFinalArquivo);
		
		em.persist(recurso);
		
		return recurso;
	}
	
	public byte[] carregarArquivo(ArquivoRecurso recurso) throws IOException {
		Path diretorioRecurso = construirCaminho(DIRETORIO.toString(), recurso.getNomeDiretorio(), recurso.getNomeArquivo());
		return Files.readAllBytes(diretorioRecurso);
	}
	
	/**
	 * Constrói uma referência à um diretório de arquivos a partir de uma lista de strings.
	 * Caso haja mais que uma string, os diretórios serão construídos dessa forma:
	 * diretorio1/diretorio2/diretorio3/diretorio.../arquivo.png
	 * 
	 * @param caminhos
	 * @return
	 */
	public static Path construirCaminho(String... caminhos) {
		String pathFinal = "";
		
		if (caminhos.length > 1) {
			pathFinal += caminhos[0];
			
			for (int i = 1; i < caminhos.length; i++) {
				pathFinal += File.separator + caminhos[i];
			}
		}
		
		else if (caminhos.length > 0) {
			pathFinal += caminhos[0];
		}
		
		return Paths.get(pathFinal);
	}
}
