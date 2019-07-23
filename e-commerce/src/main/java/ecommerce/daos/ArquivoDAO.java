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

import ecommerce.control.Transactional;
import ecommerce.models.ArquivoRecurso;

@Named
public class ArquivoDAO implements Serializable {
	private static final File DIRETORIO = new File(System.getProperty("user.home") + File.separator + "ArquivosECommerce" + File.separator);
	private final SecureRandom geradorAleatorio;
	
	@Inject
	private EntityManager em;
	
	public ArquivoDAO() {
		geradorAleatorio = new SecureRandom();
		DIRETORIO.mkdirs();
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
	
	@Transactional
	public ArquivoRecurso salvarArquivo(String nomeArquivo, String nomeDiretorio, byte[] dadosArquivo) throws IOException {
		File dir = new File(DIRETORIO.getAbsolutePath() + nomeDiretorio + File.separator);
		dir.mkdirs();
		
		String extensao = "";
		int indice = nomeArquivo.lastIndexOf(".");
		
		if (indice >= 0) {
			extensao = nomeArquivo.substring(indice);
		}
		
		String novoNomeArq = gerarNomeAleatorio(32) + "." + extensao;
		Path arquivoPath = Paths.get(dir.getAbsolutePath() + novoNomeArq);
		Files.write(arquivoPath, dadosArquivo, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		
		ArquivoRecurso recurso = new ArquivoRecurso();
		recurso.setNomeArquivo(nomeArquivo);
		recurso.setNomeDiretorio(nomeDiretorio);
		
		em.persist(recurso);
		
		return recurso;
	}
}
